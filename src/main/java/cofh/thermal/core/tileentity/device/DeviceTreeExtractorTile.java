package cofh.thermal.core.tileentity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.inventory.container.device.DeviceTreeExtractorContainer;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.TANK_MEDIUM;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_TREE_EXTRACTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceTreeExtractorTile extends DeviceTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_FILTER);

    protected static final int NUM_LEAVES = 3;
    protected static int timeConstant = 500;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> filter.valid(item) && TreeExtractorManager.instance().validBoost(item));
    protected FluidStorageCoFH outputTank = new FluidStorageCoFH(TANK_MEDIUM);

    protected boolean cached;
    protected boolean valid;

    protected BlockPos trunkPos;
    protected final BlockPos[] leafPos = new BlockPos[NUM_LEAVES];

    protected int process = timeConstant / 2;

    protected int boostCycles;
    protected int boostMax = TreeExtractorManager.instance().getDefaultEnergy();
    protected float boostMult;

    public static void setTimeConstant(int configConstant) {

        timeConstant = configConstant;
    }

    public DeviceTreeExtractorTile() {

        super(DEVICE_TREE_EXTRACTOR_TILE);

        inventory.addSlot(inputSlot, INPUT);

        tankInv.addTank(outputTank, OUTPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();

        trunkPos = new BlockPos(pos);
        for (int i = 0; i < NUM_LEAVES; ++i) {
            leafPos[i] = new BlockPos(pos);
        }
    }

    @Override
    protected void updateValidity() {

        if (world == null || !world.isAreaLoaded(pos, 1) || Utils.isClientWorld(world)) {
            return;
        }
        if (valid) {
            if (isTrunkBase(trunkPos)) {
                Set<BlockState> leafSet = TreeExtractorManager.instance().getMatchingLeaves(world.getBlockState(trunkPos));
                int leafCount = 0;
                for (int i = 0; i < NUM_LEAVES; ++i) {
                    if (leafSet.contains(world.getBlockState(leafPos[i]))) {
                        ++leafCount;
                    }
                }
                if (leafCount >= NUM_LEAVES) {
                    Iterable<BlockPos> area = BlockPos.getAllInBoxMutable(trunkPos, trunkPos.add(0, leafPos[0].getY() - trunkPos.getY(), 0));
                    for (BlockPos scan : area) {
                        Material material = world.getBlockState(scan).getMaterial();
                        if (material == Material.ORGANIC || material == Material.EARTH || material == Material.ROCK) {
                            valid = false;
                            cached = true;
                            return;
                        }
                    }
                    area = BlockPos.getAllInBoxMutable(pos.add(0, 1, 0), pos.add(0, leafPos[0].getY() - pos.getY(), 0));
                    for (BlockPos scan : area) {
                        if (isTreeExtractor(world.getBlockState(scan))) {
                            valid = false;
                            cached = true;
                            return;
                        }
                    }
                    cached = true;
                    renderFluid = TreeExtractorManager.instance().getFluid(world.getBlockState(trunkPos));
                    return;
                }
            }
            valid = false;
        }
        if (isTrunkBase(pos.west())) {
            trunkPos = pos.west();
        } else if (isTrunkBase(pos.east())) {
            trunkPos = pos.east();
        } else if (isTrunkBase(pos.north())) {
            trunkPos = pos.north();
        } else if (isTrunkBase(pos.south())) {
            trunkPos = pos.south();
        }
        if (!isTrunkBase(trunkPos)) {
            valid = false;
            cached = true;
            return;
        }
        Set<BlockState> leafSet = TreeExtractorManager.instance().getMatchingLeaves(world.getBlockState(trunkPos));
        int leafCount = 0;
        Iterable<BlockPos> area = BlockPos.getAllInBox(pos.add(-1, 0, -1), pos.add(1, Math.min(256 - pos.getY(), 40), 1)).map(BlockPos::toImmutable).collect(Collectors.toList());
        for (BlockPos scan : area) {
            if (leafSet.contains(world.getBlockState(scan))) {
                leafPos[leafCount] = new BlockPos(scan);
                ++leafCount;
                if (leafCount >= NUM_LEAVES) {
                    break;
                }
            }
        }
        if (leafCount >= NUM_LEAVES) {
            area = BlockPos.getAllInBoxMutable(trunkPos, trunkPos.add(0, leafPos[0].getY() - trunkPos.getY(), 0));
            for (BlockPos scan : area) {
                Material material = world.getBlockState(scan).getMaterial();
                if (material == Material.ORGANIC || material == Material.EARTH || material == Material.ROCK) {
                    valid = false;
                    cached = true;
                    return;
                }
            }
            area = BlockPos.getAllInBoxMutable(pos.add(0, 1, 0), pos.add(0, leafPos[0].getY() - pos.getY(), 0));
            for (BlockPos scan : area) {
                if (isTreeExtractor(world.getBlockState(scan))) {
                    valid = false;
                    cached = true;
                    return;
                }
            }
            valid = true;
            renderFluid = TreeExtractorManager.instance().getFluid(world.getBlockState(trunkPos));
        }
        cached = true;
    }

    @Override
    protected void updateActiveState() {

        if (!cached) {
            updateValidity();
        }
        super.updateActiveState();
    }

    @Override
    protected boolean isValid() {

        return valid;
    }

    @Override
    public void tick() {

        updateActiveState();

        --process;
        if (process > 0) {
            return;
        }
        updateValidity();
        process = getTimeConstant();

        if (!isActive) {
            return;
        }
        Fluid curFluid = renderFluid.getFluid();

        if (valid) {
            if (boostCycles > 0) {
                --boostCycles;
            } else if (!inputSlot.isEmpty()) {
                boostCycles = TreeExtractorManager.instance().getBoostCycles(inputSlot.getItemStack());
                boostMax = boostCycles;
                boostMult = TreeExtractorManager.instance().getBoostOutputMod(inputSlot.getItemStack());
                inputSlot.consume(1);
            } else {
                boostCycles = 0;
                boostMult = 1.0F;
            }
            outputTank.fill(new FluidStack(renderFluid, (int) (renderFluid.getAmount() * baseMod * boostMult)), EXECUTE);
        }
        if (curFluid != renderFluid.getFluid()) {
            TileStatePacket.sendToClient(this);
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData() {

        return new ModelDataMap.Builder()
                .withInitial(FLUID, renderFluid)
                .build();
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceTreeExtractorContainer(i, world, pos, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledDuration(int scale) {

        return !isActive || boostCycles <= 0 || boostMax <= 0 ? 0 : scale * boostCycles / boostMax;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public void handleControlPacket(PacketBuffer buffer) {

        super.handleControlPacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(boostCycles);
        buffer.writeInt(boostMax);
        buffer.writeFloat(boostMult);

        return buffer;
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        boostCycles = buffer.readInt();
        boostMax = buffer.readInt();
        boostMult = buffer.readFloat();
    }

    // STATE
    @Override
    public PacketBuffer getStatePacket(PacketBuffer buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(process);

        return buffer;
    }

    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        process = buffer.readInt();

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        boostCycles = nbt.getInt(TAG_BOOST_CYCLES);
        boostMax = nbt.getInt(TAG_BOOST_MAX);
        boostMult = nbt.getFloat(TAG_BOOST_MULT);
        process = nbt.getInt(TAG_PROCESS);

        for (int i = 0; i < NUM_LEAVES; ++i) {
            leafPos[i] = NBTUtil.readBlockPos(nbt.getCompound("Leaf" + i));
        }
        trunkPos = NBTUtil.readBlockPos(nbt.getCompound("Trunk"));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putInt(TAG_BOOST_CYCLES, boostCycles);
        nbt.putInt(TAG_BOOST_MAX, boostMax);
        nbt.putFloat(TAG_BOOST_MULT, boostMult);
        nbt.putInt(TAG_PROCESS, process);

        for (int i = 0; i < NUM_LEAVES; ++i) {
            nbt.put("Leaf" + i, NBTUtil.writeBlockPos(leafPos[i]));
        }
        nbt.put("Trunk", NBTUtil.writeBlockPos(trunkPos));
        return nbt;
    }
    // endregion

    // region HELPERS
    protected int getTimeConstant() {

        if (world == null) {
            return timeConstant;
        }
        int constant = timeConstant / 2;
        Iterable<BlockPos> area = BlockPos.getAllInBoxMutable(trunkPos.add(-1, 0, -1), trunkPos.add(1, 0, 1));
        for (BlockPos scan : area) {
            if (isTreeExtractor(world.getBlockState(scan))) {
                constant += timeConstant / 2;
            }
        }
        return MathHelper.clamp(constant, timeConstant, timeConstant * 2);
    }

    protected boolean isTrunkBase(BlockPos checkPos) {

        BlockState state = world.getBlockState(checkPos.down());
        Material material = state.getMaterial();
        if (material != Material.ORGANIC && material != Material.EARTH && material != Material.ROCK) {
            return false;
        }
        return TreeExtractorManager.instance().validTrunk(world.getBlockState(checkPos))
                && TreeExtractorManager.instance().validTrunk(world.getBlockState(checkPos.up()))
                && TreeExtractorManager.instance().validTrunk(world.getBlockState(checkPos.up(2)));
    }

    protected boolean isTreeExtractor(BlockState state) {

        return state.getBlock() == this.getBlockState().getBlock();
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}

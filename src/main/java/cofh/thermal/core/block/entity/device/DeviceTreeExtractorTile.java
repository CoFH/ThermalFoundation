package cofh.thermal.core.block.entity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.lib.block.entity.ICoFHTickableTile;
import cofh.lib.fluid.FluidStorageCoFH;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceTreeExtractorContainer;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
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
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceTreeExtractorTile extends DeviceTileBase implements ICoFHTickableTile.IServerTickable {

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

    public DeviceTreeExtractorTile(BlockPos pos, BlockState state) {

        super(DEVICE_TREE_EXTRACTOR_TILE, pos, state);

        inventory.addSlot(inputSlot, INPUT);

        tankInv.addTank(outputTank, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();

        trunkPos = new BlockPos(worldPosition);
        for (int i = 0; i < NUM_LEAVES; ++i) {
            leafPos[i] = new BlockPos(worldPosition);
        }
    }

    @Override
    protected void updateValidity() {

        if (level == null || !level.isAreaLoaded(worldPosition, 1) || Utils.isClientWorld(level)) {
            return;
        }
        if (valid) {
            if (isTrunkBase(trunkPos)) {
                Set<BlockState> leafSet = TreeExtractorManager.instance().getMatchingLeaves(level.getBlockState(trunkPos));
                int leafCount = 0;
                for (int i = 0; i < NUM_LEAVES; ++i) {
                    if (leafSet.contains(level.getBlockState(leafPos[i]))) {
                        ++leafCount;
                    }
                }
                if (leafCount >= NUM_LEAVES) {
                    Iterable<BlockPos> area = BlockPos.betweenClosed(trunkPos, trunkPos.offset(0, leafPos[0].getY() - trunkPos.getY(), 0));
                    for (BlockPos scan : area) {
                        Material material = level.getBlockState(scan).getMaterial();
                        if (material == Material.GRASS || material == Material.DIRT || material == Material.STONE) {
                            valid = false;
                            cached = true;
                            return;
                        }
                    }
                    area = BlockPos.betweenClosed(worldPosition.offset(0, 1, 0), worldPosition.offset(0, leafPos[0].getY() - worldPosition.getY(), 0));
                    for (BlockPos scan : area) {
                        if (isTreeExtractor(level.getBlockState(scan))) {
                            valid = false;
                            cached = true;
                            return;
                        }
                    }
                    cached = true;
                    renderFluid = TreeExtractorManager.instance().getFluid(level.getBlockState(trunkPos));
                    return;
                }
            }
            valid = false;
        }
        if (isTrunkBase(worldPosition.west())) {
            trunkPos = worldPosition.west();
        } else if (isTrunkBase(worldPosition.east())) {
            trunkPos = worldPosition.east();
        } else if (isTrunkBase(worldPosition.north())) {
            trunkPos = worldPosition.north();
        } else if (isTrunkBase(worldPosition.south())) {
            trunkPos = worldPosition.south();
        }
        if (!isTrunkBase(trunkPos)) {
            valid = false;
            cached = true;
            return;
        }
        Set<BlockState> leafSet = TreeExtractorManager.instance().getMatchingLeaves(level.getBlockState(trunkPos));
        int leafCount = 0;
        Iterable<BlockPos> area = BlockPos.betweenClosedStream(worldPosition.offset(-1, 0, -1), worldPosition.offset(1, Math.min(256 - worldPosition.getY(), 40), 1)).map(BlockPos::immutable).collect(Collectors.toList());
        for (BlockPos scan : area) {
            if (leafSet.contains(level.getBlockState(scan))) {
                leafPos[leafCount] = new BlockPos(scan);
                ++leafCount;
                if (leafCount >= NUM_LEAVES) {
                    break;
                }
            }
        }
        if (leafCount >= NUM_LEAVES) {
            area = BlockPos.betweenClosed(trunkPos, trunkPos.offset(0, leafPos[0].getY() - trunkPos.getY(), 0));
            for (BlockPos scan : area) {
                Material material = level.getBlockState(scan).getMaterial();
                if (material == Material.GRASS || material == Material.DIRT || material == Material.STONE) {
                    valid = false;
                    cached = true;
                    return;
                }
            }
            area = BlockPos.betweenClosed(worldPosition.offset(0, 1, 0), worldPosition.offset(0, leafPos[0].getY() - worldPosition.getY(), 0));
            for (BlockPos scan : area) {
                if (isTreeExtractor(level.getBlockState(scan))) {
                    valid = false;
                    cached = true;
                    return;
                }
            }
            valid = true;
            renderFluid = TreeExtractorManager.instance().getFluid(level.getBlockState(trunkPos));
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
    public void tickServer() {

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
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceTreeExtractorContainer(i, level, worldPosition, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledDuration(int scale) {

        return !isActive || boostCycles <= 0 || boostMax <= 0 ? 0 : scale * boostCycles / boostMax;
    }
    // endregion

    // region NETWORK
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        super.onDataPacket(net, pkt);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // CONTROL
    @Override
    public void handleControlPacket(FriendlyByteBuf buffer) {

        super.handleControlPacket(buffer);

        ModelDataManager.requestModelDataRefresh(this);
    }

    // GUI
    @Override
    public FriendlyByteBuf getGuiPacket(FriendlyByteBuf buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(boostCycles);
        buffer.writeInt(boostMax);
        buffer.writeFloat(boostMult);

        return buffer;
    }

    @Override
    public void handleGuiPacket(FriendlyByteBuf buffer) {

        super.handleGuiPacket(buffer);

        boostCycles = buffer.readInt();
        boostMax = buffer.readInt();
        boostMult = buffer.readFloat();
    }

    // STATE
    @Override
    public FriendlyByteBuf getStatePacket(FriendlyByteBuf buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(process);

        return buffer;
    }

    @Override
    public void handleStatePacket(FriendlyByteBuf buffer) {

        super.handleStatePacket(buffer);

        process = buffer.readInt();

        ModelDataManager.requestModelDataRefresh(this);
    }
    // endregion

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        boostCycles = nbt.getInt(TAG_BOOST_CYCLES);
        boostMax = nbt.getInt(TAG_BOOST_MAX);
        boostMult = nbt.getFloat(TAG_BOOST_MULT);
        process = nbt.getInt(TAG_PROCESS);

        for (int i = 0; i < NUM_LEAVES; ++i) {
            leafPos[i] = NbtUtils.readBlockPos(nbt.getCompound("Leaf" + i));
        }
        trunkPos = NbtUtils.readBlockPos(nbt.getCompound("Trunk"));
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_BOOST_CYCLES, boostCycles);
        nbt.putInt(TAG_BOOST_MAX, boostMax);
        nbt.putFloat(TAG_BOOST_MULT, boostMult);
        nbt.putInt(TAG_PROCESS, process);

        for (int i = 0; i < NUM_LEAVES; ++i) {
            nbt.put("Leaf" + i, NbtUtils.writeBlockPos(leafPos[i]));
        }
        nbt.put("Trunk", NbtUtils.writeBlockPos(trunkPos));
    }
    // endregion

    // region HELPERS
    protected int getTimeConstant() {

        if (level == null) {
            return timeConstant;
        }
        int constant = timeConstant / 2;
        Iterable<BlockPos> area = BlockPos.betweenClosed(trunkPos.offset(-1, 0, -1), trunkPos.offset(1, 0, 1));
        for (BlockPos scan : area) {
            if (isTreeExtractor(level.getBlockState(scan))) {
                constant += timeConstant / 2;
            }
        }
        return MathHelper.clamp(constant, timeConstant, timeConstant * 2);
    }

    protected boolean isTrunkBase(BlockPos checkPos) {

        BlockState state = level.getBlockState(checkPos.below());
        Material material = state.getMaterial();
        if (material != Material.GRASS && material != Material.DIRT && material != Material.STONE) {
            return false;
        }
        return TreeExtractorManager.instance().validTrunk(level.getBlockState(checkPos))
                && TreeExtractorManager.instance().validTrunk(level.getBlockState(checkPos.above()))
                && TreeExtractorManager.instance().validTrunk(level.getBlockState(checkPos.above(2)));
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

package cofh.thermal.core.tileentity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.thermal.core.inventory.container.device.DeviceRockGenContainer;
import cofh.thermal.core.util.managers.device.RockGenManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.BUCKET_VOLUME;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.ItemHelper.itemsEqualWithTags;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_ROCK_GEN_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceRockGenTile extends DeviceTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE);

    protected static final Supplier<ItemStack> COBBLESTONE = () -> new ItemStack(Blocks.COBBLESTONE, 0);

    protected ItemStorageCoFH outputSlot = new ItemStorageCoFH(e -> false).setEmptyItem(COBBLESTONE).setEnabled(() -> isActive);

    protected Block below = Blocks.AIR;
    protected Block adjacent = Blocks.AIR;
    protected int adjLava = 0;

    protected boolean cached;
    protected boolean valid;

    protected int process;
    protected int processMax = RockGenManager.instance().getDefaultEnergy();
    protected int genAmount = 1;

    public DeviceRockGenTile() {

        super(DEVICE_ROCK_GEN_TILE);

        inventory.addSlot(outputSlot, OUTPUT);

        addAugmentSlots(deviceAugments);
        initHandlers();

        renderFluid = new FluidStack(Fluids.LAVA, BUCKET_VOLUME);
    }

    public Block getBelow() {

        return below;
    }

    public Block getAdjacent() {

        return adjacent;
    }

    public int getAdjLava() {

        return adjLava;
    }

    @Override
    protected void updateValidity() {

        if (world == null || !world.isAreaLoaded(pos, 1)) {
            return;
        }
        adjLava = 0;
        valid = false;

        Block[] adjBlocks = new Block[4];
        BlockPos[] cardinals = new BlockPos[]{
                pos.north(),
                pos.south(),
                pos.west(),
                pos.east(),
        };
        for (int i = 0; i < 4; ++i) {
            BlockPos adj = cardinals[i];
            FluidState fluidState = world.getFluidState(adj);
            if (fluidState.getFluid().equals(Fluids.LAVA)) {
                ++adjLava;
            }
            adjBlocks[i] = fluidState.isEmpty() || fluidState.isSource() ? world.getBlockState(adj).getBlock() : Blocks.AIR;
        }
        if (adjLava > 0) {
            Block under = world.getBlockState(pos.down()).getBlock();
            RockGenManager.RockGenRecipe recipe = RockGenManager.instance().getResult(under, adjBlocks);
            ItemStack result = recipe.getResult();
            if (!result.isEmpty()) {
                outputSlot.setEmptyItem(() -> new ItemStack(result.getItem(), 0));

                if (!outputSlot.isEmpty() && !itemsEqualWithTags(result, outputSlot.getItemStack())) {
                    outputSlot.clear();
                }
                Block prevBelow = below;
                Block prevAdj = adjacent;

                below = recipe.getBelow();
                adjacent = recipe.getAdjacent();

                if (below != prevBelow || adjacent != prevAdj) {
                    TileStatePacket.sendToClient(this);
                }
                processMax = recipe.getTime();
                genAmount = Math.max(1, result.getCount());
                if (world.getBiome(pos).getCategory() == Biome.Category.NETHER) {
                    processMax = Math.max(1, processMax / 2);
                }
                process = processMax;
                valid = true;
            }
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

        if (!isActive) {
            return;
        }
        --process;
        if (process > 0) {
            return;
        }
        process = processMax;
        outputSlot.modify((int) (genAmount * baseMod));
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

        return new DeviceRockGenContainer(i, world, pos, inventory, player);
    }

    // region GUI
    @Override
    public int getScaledProgress(int scale) {

        if (!isActive || processMax <= 0 || outputSlot.isFull()) {
            return 0;
        }
        return scale * (processMax - process) / processMax;
    }
    // endregion

    // region NETWORK
    @Override
    public PacketBuffer getGuiPacket(PacketBuffer buffer) {

        super.getGuiPacket(buffer);

        buffer.writeInt(process);
        buffer.writeInt(processMax);

        return buffer;
    }

    @Override
    public void handleGuiPacket(PacketBuffer buffer) {

        super.handleGuiPacket(buffer);

        process = buffer.readInt();
        processMax = buffer.readInt();
    }

    // STATE
    @Override
    public PacketBuffer getStatePacket(PacketBuffer buffer) {

        super.getStatePacket(buffer);

        buffer.writeInt(process);
        buffer.writeInt(adjLava);

        buffer.writeString(ForgeRegistries.BLOCKS.getKey(below).toString());
        buffer.writeString(ForgeRegistries.BLOCKS.getKey(adjacent).toString());

        return buffer;
    }

    @Override
    public void handleStatePacket(PacketBuffer buffer) {

        super.handleStatePacket(buffer);

        process = buffer.readInt();
        adjLava = buffer.readInt();

        below = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readString()));
        adjacent = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(buffer.readString()));
    }
    // endregion

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        process = nbt.getInt(TAG_PROCESS);
        processMax = nbt.getInt(TAG_PROCESS_MAX);
        adjLava = nbt.getInt("Lava");

        below = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("Below")));
        adjacent = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("Adjacent")));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putInt(TAG_PROCESS, process);
        nbt.putInt(TAG_PROCESS_MAX, processMax);
        nbt.putInt("Lava", adjLava);

        nbt.putString("Below", ForgeRegistries.BLOCKS.getKey(below).toString());
        nbt.putString("Adjacent", ForgeRegistries.BLOCKS.getKey(adjacent).toString());

        return nbt;
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}

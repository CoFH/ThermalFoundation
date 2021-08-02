package cofh.thermal.core.tileentity.device;

import cofh.lib.inventory.ItemStorageCoFH;
import cofh.lib.inventory.SimpleItemHandler;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.AugmentDataHelper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.xp.XpStorage;
import cofh.thermal.core.inventory.container.device.DeviceFisherContainer;
import cofh.thermal.core.util.managers.device.FisherManager;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static cofh.lib.util.StorageGroup.INPUT;
import static cofh.lib.util.StorageGroup.OUTPUT;
import static cofh.lib.util.constants.Constants.FACING_HORIZONTAL;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.lib.util.helpers.AugmentableHelper.getAttributeMod;
import static cofh.thermal.core.init.TCoreReferences.DEVICE_FISHER_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static cofh.thermal.lib.common.ThermalConfig.deviceAugments;

public class DeviceFisherTile extends DeviceTileBase implements ITickableTileEntity {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_AREA_EFFECT, TAG_AUGMENT_TYPE_FILTER);

    protected static int timeConstant = 4800;
    protected static int minTimeConstant = timeConstant / 20;
    protected static int timeReductionWater = 20;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> FisherManager.instance().validBoost(item));
    protected SimpleItemHandler internalHandler;

    protected boolean cached;
    protected boolean valid;

    protected static final int RADIUS = 2;
    public int radius = RADIUS;

    protected int process = timeConstant / 2;

    public static void setTimeConstant(int configConstant) {

        timeConstant = configConstant;
        minTimeConstant = timeConstant / 20;
    }

    public static void setTimeReductionWater(int configConstant) {

        timeReductionWater = configConstant;
    }

    public DeviceFisherTile() {

        super(DEVICE_FISHER_TILE);

        inventory.addSlot(inputSlot, INPUT);
        inventory.addSlots(OUTPUT, 15, item -> filter.valid(item));

        xpStorage = new XpStorage(getBaseXpStorage());

        addAugmentSlots(deviceAugments);
        initHandlers();
        internalHandler = new SimpleItemHandler(this, inventory.getOutputSlots());
    }

    @Override
    public void updateContainingBlockInfo() {

        super.updateContainingBlockInfo();
        updateValidity();
    }

    @Override
    protected void updateValidity() {

        if (world == null || !world.isAreaLoaded(pos, 1 + radius) || Utils.isClientWorld(world)) {
            return;
        }
        int facingWater = 0;
        valid = false;

        BlockState myState = getBlockState();
        BlockPos facePos = pos.offset(myState.get(FACING_HORIZONTAL));
        FluidState state = world.getFluidState(facePos);

        if (state.getFluid().equals(Fluids.WATER)) {
            BlockPos areaPos = pos.offset(myState.get(FACING_HORIZONTAL), 2);
            Iterable<BlockPos> area = BlockPos.getAllInBoxMutable(areaPos.add(-1, 0, -1), areaPos.add(1, 0, 1));
            for (BlockPos scan : area) {
                state = world.getFluidState(scan);
                if (state.getFluid().equals(Fluids.WATER)) {
                    ++facingWater;
                }
            }
            valid = facingWater >= 7;
        }
        cached = true;
    }

    @Override
    protected void updateActiveState() {

        if (!cached) {
            updateValidity();
            process = Math.max(minTimeConstant, MathHelper.RANDOM.nextInt(getTimeConstant()));
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
        process = Math.max(minTimeConstant, MathHelper.RANDOM.nextInt(getTimeConstant()));

        if (!isActive) {
            return;
        }
        if (valid) {
            LootTable table = world.getServer().getLootTableManager().getLootTableFromLocation(FisherManager.instance().getBoostLootTable(inputSlot.getItemStack()));
            LootContext.Builder contextBuilder = new LootContext.Builder((ServerWorld) world).withParameter(LootParameters.field_237457_g_, Vector3d.copy(getPos())).withRandom(world.rand);

            float lootBase = baseMod * FisherManager.instance().getBoostOutputMod(inputSlot.getItemStack());
            float lootExtra = lootBase - (int) lootBase;
            int lootCount = (int) lootBase + (MathHelper.RANDOM.nextFloat() < lootExtra ? 1 : 0);

            int caught = 0;

            for (int i = 0; i < lootCount; ++i) {
                for (ItemStack stack : table.generate(contextBuilder.build(LootParameterSets.EMPTY))) {
                    if (InventoryHelper.insertStackIntoInventory(internalHandler, stack, false).isEmpty()) {
                        ++caught;
                    }
                }
            }
            if (caught > 0) {
                if (MathHelper.RANDOM.nextFloat() < FisherManager.instance().getBoostUseChance(inputSlot.getItemStack())) {
                    inputSlot.consume(1);
                }
                if (xpStorageFeature) {
                    xpStorage.receiveXp(caught + world.rand.nextInt(2 * caught), false);
                }
                Vector3d splashVec = Vector3d.copyCenteredWithVerticalOffset(pos.offset(getBlockState().get(FACING_HORIZONTAL)), 1.0);
                ((ServerWorld) world).spawnParticle(ParticleTypes.FISHING, splashVec.x, splashVec.y, splashVec.z, 10, 0.1D, 0.0D, 0.1D, 0.02D);
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory inventory, PlayerEntity player) {

        return new DeviceFisherContainer(i, world, pos, inventory, player);
    }

    // region NBT
    @Override
    public void read(BlockState state, CompoundNBT nbt) {

        super.read(state, nbt);

        process = nbt.getInt(TAG_PROCESS);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {

        super.write(nbt);

        nbt.putInt(TAG_PROCESS, process);

        return nbt;
    }
    // endregion

    // region HELPERS
    protected int getTimeConstant() {

        if (world == null) {
            return timeConstant / 2;
        }
        int constant = timeConstant;

        BlockPos areaPos = pos.offset(getBlockState().get(FACING_HORIZONTAL), radius);
        Iterable<BlockPos> area = BlockPos.getAllInBoxMutable(areaPos.add(-radius, 1 - radius, -radius), areaPos.add(radius, -1 + radius, radius));

        for (BlockPos scan : area) {
            FluidState state = world.getFluidState(scan);
            if (state.getFluid().equals(Fluids.WATER)) {
                constant -= timeReductionWater;
            }
        }
        if (Utils.hasBiomeType(world, pos, BiomeDictionary.Type.OCEAN)) {
            constant /= 3;
        }
        if (Utils.hasBiomeType(world, pos, BiomeDictionary.Type.RIVER)) {
            constant /= 2;
        }
        if (world.isRainingAt(pos)) {
            constant /= 2;
        }
        if (inputSlot.isEmpty()) {
            constant *= 2;
        }
        return MathHelper.clamp(constant, timeConstant / 20, timeConstant);
    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }

    @Override
    protected void resetAttributes() {

        super.resetAttributes();

        radius = RADIUS;
    }

    @Override
    protected void setAttributesFromAugment(CompoundNBT augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
    }
    // endregion
}

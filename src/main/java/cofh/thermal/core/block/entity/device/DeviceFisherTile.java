package cofh.thermal.core.block.entity.device;

import cofh.lib.block.entity.IAreaEffectTile;
import cofh.lib.block.entity.ICoFHTickableTile;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
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

public class DeviceFisherTile extends DeviceTileBase implements ICoFHTickableTile.IServerTickable, IAreaEffectTile {

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
    protected AABB area;

    protected int process = timeConstant / 2;

    public static void setTimeConstant(int configConstant) {

        timeConstant = configConstant;
        minTimeConstant = timeConstant / 20;
    }

    public static void setTimeReductionWater(int configConstant) {

        timeReductionWater = configConstant;
    }

    public DeviceFisherTile(BlockPos pos, BlockState state) {

        super(DEVICE_FISHER_TILE, pos, state);

        inventory.addSlot(inputSlot, INPUT);
        inventory.addSlots(OUTPUT, 15, item -> filter.valid(item));

        xpStorage = new XpStorage(getBaseXpStorage());

        addAugmentSlots(deviceAugments);
        initHandlers();
        internalHandler = new SimpleItemHandler(this, inventory.getOutputSlots());
    }

    @Override
    public void setLevel(Level level) {

        super.setLevel(level);
        updateValidity();
    }

    @Override
    protected void updateValidity() {

        area = null;
        if (level == null || !level.isAreaLoaded(worldPosition, 1 + radius) || Utils.isClientWorld(level)) {
            return;
        }
        int facingWater = 0;
        valid = false;

        BlockState myState = getBlockState();
        BlockPos facePos = worldPosition.relative(myState.getValue(FACING_HORIZONTAL));
        FluidState state = level.getFluidState(facePos);

        if (state.getType().equals(Fluids.WATER)) {
            BlockPos areaPos = worldPosition.relative(myState.getValue(FACING_HORIZONTAL), 2);
            Iterable<BlockPos> area = BlockPos.betweenClosed(areaPos.offset(-1, 0, -1), areaPos.offset(1, 0, 1));
            for (BlockPos scan : area) {
                state = level.getFluidState(scan);
                if (state.getType().equals(Fluids.WATER)) {
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
    public void tickServer() {

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
            LootTable table = level.getServer().getLootTables().get(FisherManager.instance().getBoostLootTable(inputSlot.getItemStack()));
            LootContext.Builder contextBuilder = new LootContext.Builder((ServerLevel) level)
                    .withParameter(LootContextParams.ORIGIN, Vec3.atLowerCornerOf(getBlockPos())).withRandom(level.random);

            float lootBase = baseMod * FisherManager.instance().getBoostOutputMod(inputSlot.getItemStack());
            float lootExtra = lootBase - (int) lootBase;
            int lootCount = (int) lootBase + (MathHelper.RANDOM.nextFloat() < lootExtra ? 1 : 0);

            int caught = 0;

            for (int i = 0; i < lootCount; ++i) {
                for (ItemStack stack : table.getRandomItems(contextBuilder.create(LootContextParamSets.EMPTY))) {
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
                    xpStorage.receiveXp(caught + level.random.nextInt(2 * caught), false);
                }
                Vec3 splashVec = Vec3.upFromBottomCenterOf(worldPosition.relative(getBlockState().getValue(FACING_HORIZONTAL)), 1.0);
                ((ServerLevel) level).sendParticles(ParticleTypes.FISHING, splashVec.x, splashVec.y, splashVec.z, 10, 0.1D, 0.0D, 0.1D, 0.02D);
            }
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {

        return new DeviceFisherContainer(i, level, worldPosition, inventory, player);
    }

    // region NBT
    @Override
    public void load(CompoundTag nbt) {

        super.load(nbt);

        process = nbt.getInt(TAG_PROCESS);
        valid = nbt.getBoolean(TAG_VALID);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_PROCESS, process);
        nbt.putBoolean(TAG_VALID, valid);
    }
    // endregion

    // region HELPERS
    protected int getTimeConstant() {

        if (level == null) {
            return timeConstant / 2;
        }
        int constant = timeConstant;

        BlockPos areaPos = worldPosition.relative(getBlockState().getValue(FACING_HORIZONTAL), radius);
        Iterable<BlockPos> area = BlockPos.betweenClosed(areaPos.offset(-radius, 1 - radius, -radius), areaPos.offset(radius, -1 + radius, radius));

        for (BlockPos scan : area) {
            FluidState state = level.getFluidState(scan);
            if (state.getType().equals(Fluids.WATER)) {
                constant -= timeReductionWater;
            }
        }
        if (Utils.hasBiomeType(level, worldPosition, BiomeDictionary.Type.OCEAN)) {
            constant /= 3;
        }
        if (Utils.hasBiomeType(level, worldPosition, BiomeDictionary.Type.RIVER)) {
            constant /= 2;
        }
        if (level.isRainingAt(worldPosition)) {
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
    protected void setAttributesFromAugment(CompoundTag augmentData) {

        super.setAttributesFromAugment(augmentData);

        radius += getAttributeMod(augmentData, TAG_AUGMENT_RADIUS);
    }

    @Override
    protected void finalizeAttributes(Map<Enchantment, Integer> enchantmentMap) {

        super.finalizeAttributes(enchantmentMap);

        area = null;
    }
    // endregion

    // region IAreaEffectTile
    @Override
    public AABB getArea() {

        if (area == null) {
            if (!valid) {
                BlockPos areaPos = worldPosition.relative(getBlockState().getValue(FACING_HORIZONTAL), 2);
                area = new AABB(areaPos.offset(-1, 0, -1), areaPos.offset(2, 1, 2));
            } else {
                BlockPos areaPos = worldPosition.relative(getBlockState().getValue(FACING_HORIZONTAL), radius);
                area = new AABB(areaPos.offset(-radius, -1 - radius, -radius), areaPos.offset(1 + radius, -1 + radius, 1 + radius));
            }
        }
        return area;
    }

    @Override
    public int getColor() {

        return valid ? isActive ? 0x0088FF : 0x555555 : 0xFF0000;
    }
    // endregion
}

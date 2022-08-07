package cofh.thermal.core.block.entity.device;

import cofh.core.network.packet.client.TileStatePacket;
import cofh.core.util.helpers.AugmentDataHelper;
import cofh.lib.api.block.entity.ITickableTile;
import cofh.lib.content.fluid.FluidStorageCoFH;
import cofh.lib.content.inventory.ItemStorageCoFH;
import cofh.lib.util.Utils;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.core.config.ThermalCoreConfig;
import cofh.thermal.core.inventory.container.device.DeviceTreeExtractorContainer;
import cofh.thermal.core.util.managers.device.TreeExtractorManager;
import cofh.thermal.core.util.recipes.device.TreeExtractorRecipe;
import cofh.thermal.lib.tileentity.DeviceTileBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cofh.core.client.renderer.model.ModelUtils.FLUID;
import static cofh.lib.api.StorageGroup.INPUT;
import static cofh.lib.api.StorageGroup.OUTPUT;
import static cofh.lib.util.Constants.TANK_MEDIUM;
import static cofh.lib.util.constants.NBTTags.*;
import static cofh.thermal.core.init.TCoreTileEntities.DEVICE_TREE_EXTRACTOR_TILE;
import static cofh.thermal.lib.common.ThermalAugmentRules.createAllowValidator;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;

public class DeviceTreeExtractorTile extends DeviceTileBase implements ITickableTile.IServerTickable {

    public static final BiPredicate<ItemStack, List<ItemStack>> AUG_VALIDATOR = createAllowValidator(TAG_AUGMENT_TYPE_UPGRADE, TAG_AUGMENT_TYPE_FLUID, TAG_AUGMENT_TYPE_FILTER);

    protected static final int LEAF_SEARCH_DIST = 4;
    protected static final BlockPos[] TRUNK_SEARCH = {BlockPos.ZERO,
            new BlockPos(1, 0, 0), new BlockPos(-1, 0, 1), new BlockPos(-1, 0, -1), new BlockPos(1, 0, -1),
            new BlockPos(1, 0, 0), new BlockPos(0, 0, 2), new BlockPos(-2, 0, 0), new BlockPos(0, 0, -2)};
    protected static final Direction[] CARDINAL = {Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH};
    protected static int timeConstant = 500;

    protected ItemStorageCoFH inputSlot = new ItemStorageCoFH(item -> filter.valid(item) && TreeExtractorManager.instance().validBoost(item));
    protected FluidStorageCoFH outputTank = new FluidStorageCoFH(TANK_MEDIUM);

    protected boolean cached;
    protected boolean valid;

    protected BlockPos[] logs;
    protected BlockPos[] leaves;
    protected TreeExtractorRecipe recipe;

    protected int process = timeConstant / 2;

    protected int boostCycles;
    protected int boostMax = TreeExtractorManager.instance().getDefaultEnergy();
    protected float boostMult;

    public static void setTimeConstant(int configConstant) {

        timeConstant = configConstant;
    }

    public DeviceTreeExtractorTile(BlockPos pos, BlockState state) {

        super(DEVICE_TREE_EXTRACTOR_TILE.get(), pos, state);

        inventory.addSlot(inputSlot, INPUT);
        tankInv.addTank(outputTank, OUTPUT);

        addAugmentSlots(ThermalCoreConfig.deviceAugments);
        initHandlers();

        logs = new BlockPos[]{worldPosition.immutable()};
        leaves = new BlockPos[]{worldPosition.immutable()};
    }

    @Override
    protected void updateValidity() {

        if (level == null || !level.isAreaLoaded(worldPosition, 1) || Utils.isClientWorld(level)) {
            return;
        }
        cached = true;
        if (valid && recipe != null) {
            Predicate<BlockState> validLeaf = recipe.getLeaves();
            Predicate<BlockState> validLog = recipe.getTrunk();
            if (Arrays.stream(leaves).allMatch(pos -> validLeaf.test(level.getBlockState(pos))) && Arrays.stream(logs).allMatch(pos -> validLog.test(level.getBlockState(pos)))) {
                return;
            }
        }
        for (Direction dir : CARDINAL) {
            TreeInfo info = detectTree(worldPosition.relative(dir));
            if (info.recipe != null) {
                valid = true;
                renderFluid = info.recipe.getFluid();
                leaves = info.leaves;
                logs = info.logs;
                recipe = info.recipe;
                return;
            }
        }
        valid = false;
        logs = new BlockPos[]{worldPosition.immutable()};
        leaves = new BlockPos[]{worldPosition.immutable()};
        recipe = null;
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
        if (process > 0 || !isActive) {
            return;
        }
        updateValidity();
        process = getTimeConstant();
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
            float sizeMult = MathHelper.sqrt((float) Math.min(logs.length, recipe.getMaxHeight()) * Math.min(leaves.length, recipe.getMaxLeaves()) / (recipe.getMinHeight() * recipe.getMinLeaves()));
            outputTank.fill(new FluidStack(renderFluid, (int) (renderFluid.getAmount() * baseMod * boostMult * sizeMult)), EXECUTE);
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
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {

        super.saveAdditional(nbt);

        nbt.putInt(TAG_BOOST_CYCLES, boostCycles);
        nbt.putInt(TAG_BOOST_MAX, boostMax);
        nbt.putFloat(TAG_BOOST_MULT, boostMult);
        nbt.putInt(TAG_PROCESS, process);
    }
    // endregion

    // region HELPERS
    protected int getTimeConstant() {

        if (level == null) {
            return timeConstant;
        }
        int constant = timeConstant / 2;
        BlockPos base = logs[0];
        Iterable<BlockPos> area = BlockPos.betweenClosed(base.offset(-1, 0, -1), base.offset(1, 0, 1));
        for (BlockPos scan : area) {
            if (isTreeExtractor(level.getBlockState(scan))) {
                constant += timeConstant / 2;
            }
        }
        return MathHelper.clamp(constant, timeConstant, timeConstant * 2);
    }

    protected boolean isTreeExtractor(BlockState state) {

        return state.getBlock() == this.getBlockState().getBlock();
    }

    protected TreeInfo detectTree(BlockPos basePos) {

        BlockState base = level.getBlockState(basePos);
        if (!TreeExtractorManager.instance().getValidLogs().contains(base)) {
            return new TreeInfo();
        }
        // Find recipes matching trunk
        Collection<TreeExtractorRecipe> recipes = new HashSet<>(TreeExtractorManager.instance().getRecipes());
        recipes.removeIf(recipe -> !recipe.getTrunk().test(base));
        if (recipes.isEmpty()) {
            return new TreeInfo();
        }

        // Split recipes by growth direction
        TreeInfo result = detectTreeDirection(recipes, basePos, Direction.UP);
        return result.recipe == null ? detectTreeDirection(recipes, basePos, Direction.DOWN) : result;
    }

    protected TreeInfo detectTreeDirection(Collection<TreeExtractorRecipe> recipes, BlockPos base, Direction growth) {

        recipes = recipes.stream().filter(recipe -> {
            Block sapling = recipe.getSapling();
            if (sapling != null) {
                return sapling.defaultBlockState().canSurvive(level, base);
            }
            Material material = level.getBlockState(base.relative(growth, -1)).getMaterial();
            return material == Material.GRASS || material == Material.DIRT || material == Material.STONE;
        }).collect(Collectors.toList());
        if (recipes.isEmpty()) {
            return new TreeInfo();
        }

        // Traverse tree to find logs
        // TODO: count leaves on the way?
        int min = level.getMinBuildHeight();
        int max = level.getMaxBuildHeight();
        Collection<BlockPos> logs = new ArrayList<>();
        logs.add(base.immutable());
        BlockPos.MutableBlockPos log = base.mutable();
        while (!recipes.isEmpty() && log.getY() < max && log.getY() > min) {
            Collection<TreeExtractorRecipe> matching = recipes;
            BlockPos.MutableBlockPos scan = log.mutable().move(growth);
            for (BlockPos offset : TRUNK_SEARCH) {
                scan.move(offset);
                BlockState state = level.getBlockState(scan);
                matching = recipes.stream().filter(recipe -> recipe.getTrunk().test(state)).collect(Collectors.toList());
                if (!matching.isEmpty()) {
                    logs.add(scan.immutable());
                    log = scan;
                    break;
                }
            }
            if (matching.isEmpty()) {
                break;
            }
            recipes = matching;
        }
        int height = logs.size();
        recipes.removeIf(recipe -> recipe.getMinHeight() > height);
        if (recipes.isEmpty()) {
            return new TreeInfo();
        }
        BlockPos top = log.immutable();

        // Find number of leaves around top log
        Map<TreeExtractorRecipe, Collection<BlockPos>> leaves = new HashMap<>();
        recipes.forEach(recipe -> leaves.put(recipe, new HashSet<>(recipe.getMaxLeaves())));
        leaves.put(null, new HashSet<>(129));
        leaves.get(null).add(top);
        Queue<BlockPos> q = new ArrayDeque<>();
        q.add(top);
        while (!q.isEmpty()) {
            BlockPos pos = q.poll();
            for (Direction dir : Direction.values()) {
                BlockPos adj = pos.relative(dir);
                if (adj.distManhattan(top) <= LEAF_SEARCH_DIST && leaves.values().stream().noneMatch(l -> l.contains(adj))) {
                    boolean add = false;
                    BlockState state = level.getBlockState(adj);
                    for (Map.Entry<TreeExtractorRecipe, Collection<BlockPos>> entry : leaves.entrySet()) {
                        TreeExtractorRecipe recipe = entry.getKey();
                        if (recipe == null) {
                            continue;
                        }
                        if (recipe.getLeaves().test(state)) {
                            Collection<BlockPos> blocks = entry.getValue();
                            blocks.add(adj);
                            add = true;
                            if (blocks.size() >= recipe.getMaxLeaves()) {
                                return new TreeInfo(recipe, logs.toArray(BlockPos[]::new), leaves.get(recipe).toArray(BlockPos[]::new));
                            }
                        } else if (recipe.getTrunk().test(state)) {
                            leaves.get(null).add(adj);
                            add = true;
                        }
                    }
                    if (add) {
                        q.add(adj);
                    }
                }
            }
        }
        Iterator<TreeExtractorRecipe> iter = recipes.iterator();
        TreeExtractorRecipe recipe = iter.next();
        int numLeaves = leaves.get(recipe).size();
        while (iter.hasNext()) {
            TreeExtractorRecipe compare = iter.next();
            int num = leaves.get(compare).size();
            if (num > numLeaves) {
                numLeaves = num;
                recipe = compare;
            }
        }
        if (recipe.getMinLeaves() > numLeaves) {
            return new TreeInfo();
        }
        return new TreeInfo(recipe, logs.toArray(BlockPos[]::new), leaves.get(recipe).toArray(BlockPos[]::new));
    }

    public static record TreeInfo(TreeExtractorRecipe recipe, BlockPos[] logs, BlockPos[] leaves) {

        public TreeInfo() {

            this(null, null, null);
        }

    }
    // endregion

    // region AUGMENTS
    @Override
    protected Predicate<ItemStack> augValidator() {

        return item -> AugmentDataHelper.hasAugmentData(item) && AUG_VALIDATOR.test(item, getAugmentsAsList());
    }
    // endregion
}

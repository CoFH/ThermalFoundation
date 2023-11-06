package cofh.thermal.foundation.init;

import cofh.core.item.BlockItemCoFH;
import cofh.lib.block.OreBlockCoFH;
import cofh.lib.util.Utils;
import cofh.thermal.foundation.world.level.block.grower.RubberTreeGrower;
import com.google.common.collect.ImmutableSet;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashMap;
import java.util.Map;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.foundation.ThermalFoundation.WOOD_TYPE_RUBBERWOOD;
import static cofh.thermal.foundation.init.TFndIDs.*;
import static cofh.thermal.lib.common.ThermalCreativeTabs.blocksTab;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.material.MapColor.*;

public class TFndBlocks {

    private TFndBlocks() {

    }

    public static void register() {

        registerResources();
        registerStorage();
        registerWoodBlocks();
    }

    public static void setup() {

        FireBlock fire = (FireBlock) Blocks.FIRE;

        fire.setFlammable(BLOCKS.get(ID_RUBBERWOOD_LEAVES), 30, 60);

        ComposterBlock.add(0.3F, ITEMS.get(ID_RUBBERWOOD_SAPLING));
        ComposterBlock.add(0.3F, ITEMS.get(ID_RUBBERWOOD_LEAVES));


        // AXE STRIPPING
        {
            Map<Block, Block> axeMap = new HashMap<>(AxeItem.STRIPPABLES.size() + 2);
            axeMap.putAll(AxeItem.STRIPPABLES);
            axeMap.put(BLOCKS.get(ID_RUBBERWOOD_LOG), BLOCKS.get(ID_STRIPPED_RUBBERWOOD_LOG));
            axeMap.put(BLOCKS.get(ID_RUBBERWOOD_WOOD), BLOCKS.get(ID_STRIPPED_RUBBERWOOD_WOOD));
            AxeItem.STRIPPABLES = axeMap;
        }
        // SIGN TILE STUFF
        {
            ImmutableSet.Builder<Block> builder = ImmutableSet.builder();
            builder.addAll(BlockEntityType.SIGN.validBlocks);
            builder.add(BLOCKS.get("rubberwood_sign"));
            builder.add(BLOCKS.get("rubberwood_wall_sign"));
            BlockEntityType.SIGN.validBlocks = builder.build();
        }
        // POTTED PLANTS
        {
            if (Blocks.FLOWER_POT instanceof FlowerPotBlock) {
                ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Utils.getRegistryName(BLOCKS.get(ID_RUBBERWOOD_SAPLING)), BLOCKS.getSup(ID_POTTED_RUBBERWOOD_SAPLING));
            }
        }
    }

    // region HELPERS
    private static void registerResources() {

        blocksTab(registerBlock(ID_APATITE_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_APATITE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_CINNABAR_ORE, () -> OreBlockCoFH.createStoneOre().xp(1, 3), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_CINNABAR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(1, 3), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_NITER_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_NITER_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_SULFUR_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_SULFUR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_TIN_ORE, OreBlockCoFH::createStoneOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_TIN_ORE), OreBlockCoFH::createDeepslateOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_LEAD_ORE, OreBlockCoFH::createStoneOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_LEAD_ORE), OreBlockCoFH::createDeepslateOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_SILVER_ORE, OreBlockCoFH::createStoneOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_SILVER_ORE), OreBlockCoFH::createDeepslateOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_NICKEL_ORE, OreBlockCoFH::createStoneOre, ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_NICKEL_ORE), OreBlockCoFH::createDeepslateOre, ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_RUBY_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_RUBY_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_SAPPHIRE_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(deepslate(ID_SAPPHIRE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_OIL_SAND, () -> new SandBlock(14406560, copy(Blocks.SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_SAND), new Item.Properties()).setBurnTime(2400).setModId(ID_THERMAL_FOUNDATION)));

        blocksTab(registerBlock(ID_OIL_RED_SAND, () -> new SandBlock(11098145, copy(Blocks.RED_SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_RED_SAND), new Item.Properties()).setBurnTime(2400).setModId(ID_THERMAL_FOUNDATION)));
    }

    private static void registerStorage() {

        blocksTab(registerBlock(raw(ID_TIN_BLOCK), () -> new Block(of().mapColor(TERRACOTTA_LIGHT_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(raw(ID_LEAD_BLOCK), () -> new Block(of().mapColor(TERRACOTTA_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(raw(ID_SILVER_BLOCK), () -> new Block(of().mapColor(TERRACOTTA_LIGHT_GRAY).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(raw(ID_NICKEL_BLOCK), () -> new Block(of().mapColor(TERRACOTTA_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_TIN_BLOCK, () -> new Block(of().mapColor(RAW_IRON).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_LEAD_BLOCK, () -> new Block(of().mapColor(COLOR_PURPLE).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_SILVER_BLOCK, () -> new Block(of().mapColor(RAW_IRON).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_NICKEL_BLOCK, () -> new Block(of().mapColor(RAW_IRON).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_BRONZE_BLOCK, () -> new Block(of().mapColor(COLOR_ORANGE).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_ELECTRUM_BLOCK, () -> new Block(of().mapColor(GOLD).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_INVAR_BLOCK, () -> new Block(of().mapColor(COLOR_GRAY).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_CONSTANTAN_BLOCK, () -> new Block(of().mapColor(COLOR_ORANGE).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));

        blocksTab(registerBlock(ID_RUBY_BLOCK, () -> new Block(of().mapColor(COLOR_RED).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_SAPPHIRE_BLOCK, () -> new Block(of().mapColor(COLOR_BLUE).strength(5.0F, 6.0F).sound(SoundType.METAL).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION));
    }

    private static void registerWoodBlocks() {

        blocksTab(registerBlock(ID_RUBBERWOOD_LOG, () -> Blocks.log(TERRACOTTA_GREEN, QUARTZ), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_STRIPPED_RUBBERWOOD_LOG, () -> Blocks.log(TERRACOTTA_GREEN, TERRACOTTA_GREEN), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_RUBBERWOOD_WOOD, () -> new RotatedPillarBlock(of().mapColor(QUARTZ).strength(2.0F).sound(SoundType.WOOD)), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_STRIPPED_RUBBERWOOD_WOOD, () -> new RotatedPillarBlock(of().mapColor(TERRACOTTA_GREEN).strength(2.0F).sound(SoundType.WOOD)), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_RUBBERWOOD_SAPLING, () -> new SaplingBlock(new RubberTreeGrower(), of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), ID_THERMAL_FOUNDATION));
        blocksTab(registerBlock(ID_RUBBERWOOD_LEAVES, () -> Blocks.leaves(SoundType.GRASS), ID_THERMAL_FOUNDATION));

        if (Blocks.FLOWER_POT instanceof FlowerPotBlock) {
            registerBlockOnly(ID_POTTED_RUBBERWOOD_SAPLING, () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLOCKS.getSup(ID_RUBBERWOOD_SAPLING), BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY)));
        }
        registerWoodBlockSet("rubberwood", TERRACOTTA_GREEN, 1.5F, 2.5F, SoundType.WOOD, WOOD_TYPE_RUBBERWOOD, ID_THERMAL_FOUNDATION);

        registerBlockOnly("rubberwood_sign", () -> new StandingSignBlock(of().noCollission().strength(1.0F).sound(SoundType.WOOD), WOOD_TYPE_RUBBERWOOD));
        registerBlockOnly("rubberwood_wall_sign", () -> new WallSignBlock(of().noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(BLOCKS.getSup("rubberwood_sign")), WOOD_TYPE_RUBBERWOOD));
    }
    // endregion
}

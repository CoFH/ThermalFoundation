package cofh.thermal.foundation.init;

import cofh.core.item.BlockItemCoFH;
import cofh.lib.block.OreBlockCoFH;
import cofh.lib.util.Utils;
import cofh.thermal.core.util.RegistrationHelper;
import cofh.thermal.foundation.world.level.block.grower.RubberTreeGrower;
import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.HashMap;
import java.util.Map;

import static cofh.lib.util.constants.ModIds.ID_THERMAL_FOUNDATION;
import static cofh.thermal.core.ThermalCore.BLOCKS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.util.RegistrationHelper.*;
import static cofh.thermal.foundation.ThermalFoundation.RUBBERWOOD;
import static cofh.thermal.foundation.init.TFndIDs.*;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_BLOCKS;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.copy;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;

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

        registerBlock(ID_APATITE_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_APATITE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_APATITE), ID_THERMAL_FOUNDATION);
        registerBlock(ID_CINNABAR_ORE, () -> OreBlockCoFH.createStoneOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_CINNABAR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(1, 3), getFlag(FLAG_RESOURCE_CINNABAR), ID_THERMAL_FOUNDATION);
        registerBlock(ID_NITER_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_NITER_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_NITER), ID_THERMAL_FOUNDATION);
        registerBlock(ID_SULFUR_ORE, () -> OreBlockCoFH.createStoneOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_SULFUR_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(0, 2), getFlag(FLAG_RESOURCE_SULFUR), ID_THERMAL_FOUNDATION);

        registerBlock(ID_TIN_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_TIN), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_TIN_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_TIN), ID_THERMAL_FOUNDATION);
        registerBlock(ID_LEAD_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_LEAD), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_LEAD_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_LEAD), ID_THERMAL_FOUNDATION);
        registerBlock(ID_SILVER_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_SILVER), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_SILVER_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_SILVER), ID_THERMAL_FOUNDATION);
        registerBlock(ID_NICKEL_ORE, OreBlockCoFH::createStoneOre, getFlag(FLAG_RESOURCE_NICKEL), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_NICKEL_ORE), OreBlockCoFH::createDeepslateOre, getFlag(FLAG_RESOURCE_NICKEL), ID_THERMAL_FOUNDATION);

        registerBlock(ID_RUBY_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_RUBY_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_RUBY), ID_THERMAL_FOUNDATION);
        registerBlock(ID_SAPPHIRE_ORE, () -> OreBlockCoFH.createStoneOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE), ID_THERMAL_FOUNDATION);
        registerBlock(deepslate(ID_SAPPHIRE_ORE), () -> OreBlockCoFH.createDeepslateOre().xp(3, 7), getFlag(FLAG_RESOURCE_SAPPHIRE), ID_THERMAL_FOUNDATION);

        registerBlockAndItem(ID_OIL_SAND, () -> new SandBlock(14406560, copy(Blocks.SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)).setModId(ID_THERMAL_FOUNDATION));

        registerBlockAndItem(ID_OIL_RED_SAND, () -> new SandBlock(11098145, copy(Blocks.RED_SAND)),
                () -> new BlockItemCoFH(BLOCKS.get(ID_OIL_RED_SAND), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(2400).setShowInGroups(getFlag(FLAG_RESOURCE_OIL)).setModId(ID_THERMAL_FOUNDATION));
    }

    private static void registerStorage() {

        registerBlock(raw(ID_TIN_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION);
        registerBlock(raw(ID_LEAD_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_BLUE).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION);
        registerBlock(raw(ID_SILVER_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_LIGHT_GRAY).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION);
        registerBlock(raw(ID_NICKEL_BLOCK), () -> new Block(of(Material.STONE, MaterialColor.TERRACOTTA_YELLOW).strength(5.0F, 6.0F).requiresCorrectToolForDrops()), ID_THERMAL_FOUNDATION);

        registerBlock(ID_TIN_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_TIN), ID_THERMAL_FOUNDATION);
        registerBlock(ID_LEAD_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_LEAD), ID_THERMAL_FOUNDATION);
        registerBlock(ID_SILVER_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_SILVER), ID_THERMAL_FOUNDATION);
        registerBlock(ID_NICKEL_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_NICKEL), ID_THERMAL_FOUNDATION);

        registerBlock(ID_BRONZE_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_BRONZE), ID_THERMAL_FOUNDATION);
        registerBlock(ID_ELECTRUM_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_ELECTRUM), ID_THERMAL_FOUNDATION);
        registerBlock(ID_INVAR_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_INVAR), ID_THERMAL_FOUNDATION);
        registerBlock(ID_CONSTANTAN_BLOCK, RegistrationHelper::storageBlock, getFlag(FLAG_RESOURCE_CONSTANTAN), ID_THERMAL_FOUNDATION);

        registerBlock(ID_RUBY_BLOCK, () -> storageBlock(MaterialColor.COLOR_RED), getFlag(FLAG_RESOURCE_RUBY), ID_THERMAL_FOUNDATION);
        registerBlock(ID_SAPPHIRE_BLOCK, () -> storageBlock(MaterialColor.COLOR_BLUE), getFlag(FLAG_RESOURCE_SAPPHIRE), ID_THERMAL_FOUNDATION);
    }

    private static void registerWoodBlocks() {

        registerBlock(ID_RUBBERWOOD_LOG, () -> log(MaterialColor.TERRACOTTA_GREEN, MaterialColor.QUARTZ), ID_THERMAL_FOUNDATION);
        registerBlock(ID_STRIPPED_RUBBERWOOD_LOG, () -> log(MaterialColor.TERRACOTTA_GREEN, MaterialColor.TERRACOTTA_GREEN), ID_THERMAL_FOUNDATION);
        registerBlock(ID_RUBBERWOOD_WOOD, () -> new RotatedPillarBlock(of(Material.WOOD, MaterialColor.QUARTZ).strength(2.0F).sound(SoundType.WOOD)), ID_THERMAL_FOUNDATION);
        registerBlock(ID_STRIPPED_RUBBERWOOD_WOOD, () -> new RotatedPillarBlock(of(Material.WOOD, MaterialColor.TERRACOTTA_GREEN).strength(2.0F).sound(SoundType.WOOD)), ID_THERMAL_FOUNDATION);
        registerBlock(ID_RUBBERWOOD_SAPLING, () -> new SaplingBlock(new RubberTreeGrower(), of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), ID_THERMAL_FOUNDATION);
        registerBlock(ID_RUBBERWOOD_LEAVES, () -> leaves(SoundType.GRASS), ID_THERMAL_FOUNDATION);

        if (Blocks.FLOWER_POT instanceof FlowerPotBlock) {
            registerBlock(ID_POTTED_RUBBERWOOD_SAPLING, () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, BLOCKS.getSup(ID_RUBBERWOOD_SAPLING), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
        }
        registerWoodBlockSet("rubberwood", Material.WOOD, MaterialColor.TERRACOTTA_GREEN, 1.5F, 2.5F, SoundType.WOOD, ID_THERMAL_FOUNDATION);

        registerBlockOnly("rubberwood_sign", () -> new StandingSignBlock(of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD), RUBBERWOOD));
        registerBlockOnly("rubberwood_wall_sign", () -> new WallSignBlock(of(Material.WOOD).noCollission().strength(1.0F).sound(SoundType.WOOD).lootFrom(BLOCKS.getSup("rubberwood_sign")), RUBBERWOOD));
    }
    // endregion

    // region HELPERS
    private static RotatedPillarBlock log(MaterialColor inner, MaterialColor bark) {

        return new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, (state) -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? inner : bark).strength(2.0F).sound(SoundType.WOOD));
    }

    private static LeavesBlock leaves(SoundType soundType) {

        return new LeavesBlock(BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(soundType).noOcclusion().isValidSpawn(Blocks::ocelotOrParrot).isSuffocating(Blocks::never).isViewBlocking(Blocks::never));
    }
    // endregion
}

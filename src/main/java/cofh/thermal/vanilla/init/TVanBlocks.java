//package cofh.thermal.vanilla.init;
//
//import cofh.core.item.BlockItemCoFH;
//import cofh.lib.block.impl.GunpowderBlock;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.*;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.material.Material;
//import net.minecraft.world.level.material.MaterialColor;
//
//import static cofh.thermal.core.ThermalCore.BLOCKS;
//import static cofh.thermal.core.util.RegistrationHelper.registerBlock;
//import static cofh.thermal.core.util.RegistrationHelper.registerBlockAndItem;
//import static cofh.thermal.lib.common.ThermalFlags.FLAG_VANILLA_BLOCKS;
//import static cofh.thermal.lib.common.ThermalFlags.getFlag;
//import static cofh.thermal.lib.common.ThermalIDs.*;
//import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_BLOCKS;
//import static cofh.thermal.lib.common.ThermalItemGroups.THERMAL_FOODS;
//import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
//
//public class TVanBlocks {
//
//    private TVanBlocks() {
//
//    }
//
//    public static void register() {
//
//        registerBlockAndItem(ID_CHARCOAL_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.COLOR_BLACK).strength(5.0F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
//                () -> new BlockItemCoFH(BLOCKS.get(ID_CHARCOAL_BLOCK), new Item.Properties().tab(THERMAL_BLOCKS)).setBurnTime(16000).setShowInGroups(getFlag(FLAG_VANILLA_BLOCKS)));
//        registerBlock(ID_GUNPOWDER_BLOCK, () -> new GunpowderBlock(of(Material.EXPLOSIVE, MaterialColor.COLOR_GRAY).strength(0.5F).sound(SoundType.SAND)), getFlag(FLAG_VANILLA_BLOCKS));
//        registerBlock(ID_SUGAR_CANE_BLOCK, () -> new RotatedPillarBlock(of(Material.GRASS, MaterialColor.PLANT).strength(1.0F).sound(SoundType.CROP)) {
//
//            @Override
//            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
//
//                entityIn.causeFallDamage(fallDistance, 0.6F, DamageSource.FALL);
//            }
//        }, getFlag(FLAG_VANILLA_BLOCKS));
//        registerBlock(ID_BAMBOO_BLOCK, () -> new RotatedPillarBlock(of(Material.GRASS, MaterialColor.PLANT).strength(1.0F).sound(SoundType.BAMBOO)) {
//
//            @Override
//            public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
//
//                entityIn.causeFallDamage(fallDistance, 0.8F, DamageSource.FALL);
//            }
//        }, getFlag(FLAG_VANILLA_BLOCKS));
//
//        registerBlock(ID_APPLE_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.COLOR_RED).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
//        registerBlock(ID_CARROT_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_ORANGE).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
//        registerBlock(ID_POTATO_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_BROWN).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
//        registerBlock(ID_BEETROOT_BLOCK, () -> new Block(of(Material.WOOD, MaterialColor.TERRACOTTA_RED).strength(1.5F).sound(SoundType.SCAFFOLDING)), THERMAL_FOODS, getFlag(FLAG_VANILLA_BLOCKS));
//    }
//
//    public static void setup() {
//
//        FireBlock fire = (FireBlock) Blocks.FIRE;
//        fire.setFlammable(BLOCKS.get(ID_CHARCOAL_BLOCK), 5, 5);
//        fire.setFlammable(BLOCKS.get(ID_GUNPOWDER_BLOCK), 15, 100);
//        fire.setFlammable(BLOCKS.get(ID_SUGAR_CANE_BLOCK), 60, 20);
//        fire.setFlammable(BLOCKS.get(ID_BAMBOO_BLOCK), 60, 20);
//    }
//
//}

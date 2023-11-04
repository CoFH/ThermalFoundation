//package cofh.thermal.foundation.world.level.block.grower;
//
//import cofh.thermal.foundation.data.TFndFeatures;
//import net.minecraft.core.Holder;
//import net.minecraft.util.RandomSource;
//import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
//import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
//
//import javax.annotation.Nullable;
//
//public class RubberTreeGrower extends AbstractMegaTreeGrower {
//
//    @Nullable
//    @Override
//    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource randomIn, boolean largeHive) {
//
//        return TFndFeatures.RUBBERWOOD_TREE;
//    }
//
//    @Nullable
//    @Override
//    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource rand) {
//
//        return TFndFeatures.MEGA_RUBBERWOOD_TREE;
//    }
//
//}
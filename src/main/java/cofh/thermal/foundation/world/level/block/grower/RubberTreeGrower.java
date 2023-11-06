package cofh.thermal.foundation.world.level.block.grower;

import cofh.thermal.foundation.data.worldgen.TFndFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

import javax.annotation.Nullable;

public class RubberTreeGrower extends AbstractMegaTreeGrower {

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {

        return TFndFeatures.Configured.RUBBERWOOD_TREE;
    }

    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource pRandom) {

        return TFndFeatures.Configured.MEGA_RUBBERWOOD_TREE;
    }

}
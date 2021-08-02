package cofh.thermal.core.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.core.world.gen.feature.ThermalFeatures.*;

public class ThermalBiomeFeatures {

    private ThermalBiomeFeatures() {

    }

    public static void addOreGeneration(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder generationSettingsBuilder = event.getGeneration();
        Biome.Category category = event.getCategory();

        if (isOverworldBiome(category)) {
            withApatiteOre(generationSettingsBuilder);
            withCinnabarOre(generationSettingsBuilder);
            withNiterOre(generationSettingsBuilder);
            withSulfurOre(generationSettingsBuilder);

            withCopperOre(generationSettingsBuilder);
            withTinOre(generationSettingsBuilder);
            withLeadOre(generationSettingsBuilder);
            withSilverOre(generationSettingsBuilder);
            withNickelOre(generationSettingsBuilder);
        }
    }

    public static void addOilGeneration(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder generationSettingsBuilder = event.getGeneration();
        Biome.Category category = event.getCategory();

        if (isOverworldBiome(category)) {
            if (category == Biome.Category.DESERT || category == Biome.Category.MESA) {
                withOilSand(generationSettingsBuilder);
            }
        }
    }

    public static void addHostileSpawns(BiomeLoadingEvent event) {

        MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();

        if (spawnInfoBuilder.getSpawner(EntityClassification.MONSTER).isEmpty()) {
            return;
        }
        ResourceLocation name = event.getName();
        Biome.Category category = event.getCategory();
        Biome.Climate climate = event.getClimate();

        if (isOverworldBiome(category)) {
            if (category == Biome.Category.EXTREME_HILLS || category == Biome.Category.MESA) {
                withBasalz(spawnInfoBuilder);
            }
            if (category == Biome.Category.DESERT || category == Biome.Category.MESA || category == Biome.Category.SAVANNA) {
                withBlitz(spawnInfoBuilder);
            }
            if (climate.precipitation == Biome.RainType.SNOW & climate.temperature <= 0.3F) {
                withBlizz(spawnInfoBuilder);
            }
        } else if (isNetherBiome(category)) {
            if (name != null && name.toString().equals("minecraft:basalt_deltas")) {
                withBasalz(spawnInfoBuilder);
            }
        }
    }

    // region ORES
    public static void withApatiteOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_APATITE);
    }

    public static void withCinnabarOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_CINNABAR);
    }

    public static void withNiterOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_NITER);
    }

    public static void withSulfurOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_SULFUR);
    }

    // TODO: 1.17 remove.
    public static void withCopperOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_COPPER);
    }

    public static void withTinOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_TIN);
    }

    public static void withLeadOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_LEAD);
    }

    public static void withSilverOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_SILVER);
    }

    public static void withNickelOre(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, ORE_NICKEL);
    }
    // endregion

    // region OIL
    public static void withOilSand(BiomeGenerationSettings.Builder builder) {

        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, OIL_SAND);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, OIL_RED_SAND);
    }
    // endregion

    // region MOBS
    public static void withBasalz(MobSpawnInfoBuilder builder) {

        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BASALZ_ENTITY, 10, 1, 3));
    }

    public static void withBlitz(MobSpawnInfoBuilder builder) {

        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BLITZ_ENTITY, 10, 1, 3));
    }

    public static void withBlizz(MobSpawnInfoBuilder builder) {

        builder.withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(BLIZZ_ENTITY, 10, 1, 3));
    }
    // endregion

    // region HELPERS
    public static boolean isOverworldBiome(Biome.Category category) {

        return category != Biome.Category.NONE && category != Biome.Category.THEEND && category != Biome.Category.NETHER;
    }

    public static boolean isNetherBiome(Biome.Category category) {

        return category == Biome.Category.NETHER;
    }
    // endregion
}

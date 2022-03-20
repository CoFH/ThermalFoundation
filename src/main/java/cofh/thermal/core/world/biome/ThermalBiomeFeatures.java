/*
package cofh.thermal.core.world.biome;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.core.world.gen.feature.ThermalFeatures.*;

// TODO Lemming, See ConfiguredFeatureCoFH.
public class ThermalBiomeFeatures {

    private ThermalBiomeFeatures() {

    }

    public static void addOreGeneration(BiomeLoadingEvent event) {

        BiomeGenerationSettingsBuilder generationSettingsBuilder = event.getGeneration();
        Biome.BiomeCategory category = event.getCategory();

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
        Biome.BiomeCategory category = event.getCategory();

        if (isOverworldBiome(category)) {
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA) {
                withOilSand(generationSettingsBuilder);
            }
        }
    }

    public static void addHostileSpawns(BiomeLoadingEvent event) {

        MobSpawnSettingsBuilder spawnInfoBuilder = event.getSpawns();

        if (spawnInfoBuilder.getSpawner(MobCategory.MONSTER).isEmpty()) {
            return;
        }
        ResourceLocation name = event.getName();
        Biome.BiomeCategory category = event.getCategory();
        Biome.ClimateSettings climate = event.getClimate();

        if (isOverworldBiome(category)) {
            if (category == Biome.BiomeCategory.EXTREME_HILLS || category == Biome.BiomeCategory.MESA) {
                withBasalz(spawnInfoBuilder);
            }
            if (category == Biome.BiomeCategory.DESERT || category == Biome.BiomeCategory.MESA || category == Biome.BiomeCategory.SAVANNA) {
                withBlitz(spawnInfoBuilder);
            }
            if (climate.precipitation == Biome.Precipitation.SNOW & climate.temperature <= 0.3F) {
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

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_APATITE);
    }

    public static void withCinnabarOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_CINNABAR);
    }

    public static void withNiterOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_NITER);
    }

    public static void withSulfurOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_SULFUR);
    }

    // TODO: 1.17 remove.
    public static void withCopperOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_COPPER);
    }

    public static void withTinOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_TIN);
    }

    public static void withLeadOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_LEAD);
    }

    public static void withSilverOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_SILVER);
    }

    public static void withNickelOre(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ORE_NICKEL);
    }
    // endregion

    // region OIL
    public static void withOilSand(BiomeGenerationSettings.Builder builder) {

        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OIL_SAND);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OIL_RED_SAND);
    }
    // endregion

    // region MOBS
    public static void withBasalz(MobSpawnSettingsBuilder builder) {

        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BASALZ_ENTITY, 10, 1, 3));
    }

    public static void withBlitz(MobSpawnSettingsBuilder builder) {

        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BLITZ_ENTITY, 10, 1, 3));
    }

    public static void withBlizz(MobSpawnSettingsBuilder builder) {

        builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BLIZZ_ENTITY, 10, 1, 3));
    }
    // endregion

    // region HELPERS
    public static boolean isOverworldBiome(Biome.BiomeCategory category) {

        return category != Biome.BiomeCategory.NONE && category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NETHER;
    }

    public static boolean isNetherBiome(Biome.BiomeCategory category) {

        return category == Biome.BiomeCategory.NETHER;
    }
    // endregion
}
*/

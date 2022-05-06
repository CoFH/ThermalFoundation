package cofh.thermal.core.config;

import cofh.lib.config.IBaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class ThermalClientConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Sounds");

        boolBlockAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Blocks will have ambient sounds.")
                .define("Ambient Block Sounds", true);

        boolMobAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Mobs will have ambient sounds.")
                .define("Ambient Mob Sounds", true);

        builder.pop();
    }

    @Override
    public void refresh() {

        blockAmbientSounds = boolBlockAmbientSounds.get();
        mobAmbientSounds = boolMobAmbientSounds.get();
    }

    // region VARIABLES
    public static boolean jeiBucketTanks = true;

    public static boolean blockAmbientSounds = true;
    public static boolean mobAmbientSounds = true;

    public ForgeConfigSpec.BooleanValue boolBlockAmbientSounds;
    public ForgeConfigSpec.BooleanValue boolMobAmbientSounds;
    // endregion
}

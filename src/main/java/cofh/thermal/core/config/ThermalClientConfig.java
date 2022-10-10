package cofh.thermal.core.config;

import cofh.core.config.IBaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

import static cofh.lib.util.Constants.TRUE;

public class ThermalClientConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Sounds");

        blockAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Blocks will have ambient sounds.")
                .define("Ambient Block Sounds", blockAmbientSounds);

        mobAmbientSounds = builder
                .comment("If TRUE, some 'Thermal Series' Mobs will have ambient sounds.")
                .define("Ambient Mob Sounds", mobAmbientSounds);

        builder.pop();
    }

    // region VARIABLES
    public static Supplier<Boolean> jeiBucketTanks = TRUE;

    public static Supplier<Boolean> blockAmbientSounds = TRUE;
    public static Supplier<Boolean> mobAmbientSounds = TRUE;
    // endregion
}

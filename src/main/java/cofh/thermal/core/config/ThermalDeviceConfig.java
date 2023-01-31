package cofh.thermal.core.config;

import cofh.core.config.IBaseConfig;
import cofh.thermal.core.block.entity.device.DeviceComposterTile;
import cofh.thermal.core.block.entity.device.DeviceFisherTile;
import cofh.thermal.core.block.entity.device.DeviceTreeExtractorTile;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Supplier;

import static cofh.thermal.lib.common.ThermalFlags.getFlag;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class ThermalDeviceConfig implements IBaseConfig {

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("Devices");

        if (getFlag(ID_DEVICE_TREE_EXTRACTOR).get()) {
            builder.push("TreeExtractor");

            deviceTreeExtractorTimeConstant = builder
                    .comment("This sets the base time constant (in ticks) for the Arboreal Extractor.")
                    .defineInRange("Time Constant", 500, 20, 72000);

            builder.pop();
        }
        if (getFlag(ID_DEVICE_COMPOSTER).get()) {
            builder.push("Composter");

            deviceComposterTimeConstant = builder
                    .comment("This sets the base time constant (in ticks) for the Batch Composter.")
                    .defineInRange("Time Constant", 120, 20, 72000);
            deviceComposterParticles = builder
                    .comment("If TRUE, the Batch Composter will have particle effects when operating.")
                    .define("Particles", true);

            builder.pop();
        }
        if (getFlag(ID_DEVICE_FISHER).get()) {
            builder.push("Fisher");

            deviceFisherTimeConstant = builder
                    .comment("This sets the base time constant (in ticks) for the Aquatic Entangler.")
                    .defineInRange("Time Constant", 4800, 400, 72000);
            deviceFisherTimeReductionWater = builder
                    .comment("This sets the time constant reduction (in ticks) per nearby Water source block for the Aquatic Entangler.")
                    .defineInRange("Water Source Time Constant Reduction", 20, 1, 1000);
            deviceFisherParticles = builder
                    .comment("If TRUE, the Aquatic Entangler will have particle effects when operating.")
                    .define("Particles", true);

            builder.pop();
        }
        builder.pop();
    }

    @Override
    public void refresh() {

        if (deviceTreeExtractorTimeConstant != null) {
            DeviceTreeExtractorTile.setTimeConstant(deviceTreeExtractorTimeConstant.get());
        }
        if (deviceComposterTimeConstant != null) {
            DeviceComposterTile.setTimeConstant(deviceComposterTimeConstant.get());
            DeviceComposterTile.setParticles(deviceComposterParticles.get());
        }
        if (deviceFisherTimeConstant != null) {
            DeviceFisherTile.setTimeConstant(deviceFisherTimeConstant.get());
            DeviceFisherTile.setTimeReductionWater(deviceFisherTimeReductionWater.get());
            DeviceFisherTile.setParticles(deviceFisherParticles.get());
        }
    }

    // region CONFIG VARIABLES
    private Supplier<Integer> deviceTreeExtractorTimeConstant;

    private Supplier<Integer> deviceComposterTimeConstant;
    private Supplier<Boolean> deviceComposterParticles;

    private Supplier<Integer> deviceFisherTimeConstant;
    private Supplier<Integer> deviceFisherTimeReductionWater;
    private Supplier<Boolean> deviceFisherParticles;
    // endregion
}

package cofh.thermal.core.config;

import cofh.core.config.IBaseConfig;
import cofh.core.config.world.OreConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Map;

public class ThermalWorldConfig implements IBaseConfig {

    protected static final Map<String, OreConfig> ORE_CONFIGS = new Object2ObjectOpenHashMap<>();

    public static void addOreConfig(String name, OreConfig config) {

        ORE_CONFIGS.put(name, config);
    }

    public static OreConfig getOreConfig(String name) {

        return ORE_CONFIGS.getOrDefault(name, OreConfig.EMPTY_CONFIG);
    }

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

        builder.push("World");
        builder.push("Ores");

        for (IBaseConfig config : ORE_CONFIGS.values()) {
            config.apply(builder);
        }

        builder.pop();
        builder.pop();
    }

    @Override
    public void refresh() {

        for (IBaseConfig config : ORE_CONFIGS.values()) {
            config.refresh();
        }
    }

}

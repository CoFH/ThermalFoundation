package cofh.thermal.core.config;

import cofh.lib.config.IBaseConfig;
import cofh.lib.config.world.OreConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ThermalWorldConfig implements IBaseConfig {

    protected static final Map<String, OreConfig> ORE_CONFIGS = new Object2ObjectOpenHashMap<>();

    static {
        List<ResourceKey<Level>> defaultDimensions = Collections.singletonList(Level.OVERWORLD);

        //        ORE_CONFIGS.put("tin_ore", new OreConfig("Tin", 4, 20, 60, 9, defaultDimensions));
        //        ORE_CONFIGS.put("lead_ore", new OreConfig("Lead", 2, 0, 40, 8, defaultDimensions));
        //        ORE_CONFIGS.put("silver_ore", new OreConfig("Silver", 2, 0, 40, 8, defaultDimensions));
        //        ORE_CONFIGS.put("nickel_ore", new OreConfig("Nickel", 2, 0, 120, 8, defaultDimensions));
    }

    @Override
    public void apply(ForgeConfigSpec.Builder builder) {

    }

    @Override
    public void refresh() {

    }

}

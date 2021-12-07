package cofh.thermal.lib.common;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class ThermalItemGroups {

    private ThermalItemGroups() {

    }

    public static final ItemGroup THERMAL_BLOCKS = new ItemGroup(-1, ID_THERMAL + ".blocks") {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_ENDERIUM_BLOCK));
        }
    };

    public static final ItemGroup THERMAL_DEVICES = new ItemGroup(-1, ID_THERMAL + ".devices") {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_TINKER_BENCH));
        }
    };

    public static final ItemGroup THERMAL_ITEMS = new ItemGroup(-1, ID_THERMAL + ".items") {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get("signalum_gear"));
        }
    };

    public static final ItemGroup THERMAL_CROPS = new ItemGroup(-1, ID_THERMAL + ".crops") {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_APPLE_BLOCK));
        }
    };

    public static final ItemGroup THERMAL_TOOLS = new ItemGroup(-1, ID_THERMAL + ".tools") {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_WRENCH));
        }
    };

    public static ItemGroup THERMAL_MISC; // TODO: Florbs, Morbs

}

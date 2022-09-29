package cofh.thermal.lib.common;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Comparator;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class ThermalItemGroups {

    private ThermalItemGroups() {

    }

    public static final Comparator<ItemStack> MOD_ID_COMPARISON = (stackA, stackB) -> {

        String modA = stackA.getItem().getCreatorModId(stackA);
        String modB = stackB.getItem().getCreatorModId(stackB);
        return modA == null || modB == null ? 0 : modA.compareTo(modB);
    };

    public static final CreativeModeTab THERMAL_BLOCKS = new CreativeModeTab(-1, ID_THERMAL + ".blocks") {

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {

            super.fillItemList(list);
            list.sort(MOD_ID_COMPARISON);
        }

        @Override
        @OnlyIn (Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_ENDERIUM_BLOCK));
        }
    };

    public static final CreativeModeTab THERMAL_DEVICES = new CreativeModeTab(-1, ID_THERMAL + ".devices") {

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {

            super.fillItemList(list);
            list.sort(MOD_ID_COMPARISON);
        }

        @Override
        @OnlyIn (Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_TINKER_BENCH));
        }
    };

    public static final CreativeModeTab THERMAL_ITEMS = new CreativeModeTab(-1, ID_THERMAL + ".items") {

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {

            super.fillItemList(list);
            list.sort(MOD_ID_COMPARISON);
        }

        @Override
        @OnlyIn (Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get("signalum_gear"));
        }
    };

    public static final CreativeModeTab THERMAL_FOODS = new CreativeModeTab(-1, ID_THERMAL + ".foods") {

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {

            super.fillItemList(list);
            list.sort(MOD_ID_COMPARISON);
        }

        @Override
        @OnlyIn (Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_APPLE_BLOCK));
        }
    };

    public static final CreativeModeTab THERMAL_TOOLS = new CreativeModeTab(-1, ID_THERMAL + ".tools") {

        @Override
        public void fillItemList(NonNullList<ItemStack> list) {

            super.fillItemList(list);
            list.sort(MOD_ID_COMPARISON);
        }

        @Override
        @OnlyIn (Dist.CLIENT)
        public ItemStack makeIcon() {

            return new ItemStack(ITEMS.get(ID_WRENCH));
        }
    };

    public static CreativeModeTab THERMAL_MISC; // TODO: Florbs, Morbs

}

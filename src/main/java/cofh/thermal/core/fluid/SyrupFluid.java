package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;

import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.ID_FLUID_SYRUP;

public class SyrupFluid extends FluidCoFH {

    public static SyrupFluid create() {

        return new SyrupFluid(ID_FLUID_SYRUP, "thermal:block/fluids/syrup_still", "thermal:block/fluids/syrup_flow");
    }

    protected SyrupFluid(String key, String stillTexture, String flowTexture) {

        super(FLUIDS, key, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .density(1400)
                .viscosity(2500)
                .sound(SoundEvents.ITEM_BOTTLE_FILL, SoundEvents.ITEM_BOTTLE_EMPTY)
        );

        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ThermalItemGroups.THERMAL_ITEMS)));
        properties.bucket(bucket);
    }

}

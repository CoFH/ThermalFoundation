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
import static cofh.thermal.core.init.TCoreIDs.ID_FLUID_LIGHT_OIL;

public class LightOilFluid extends FluidCoFH {

    public static LightOilFluid create() {

        return new LightOilFluid(ID_FLUID_LIGHT_OIL, "thermal:block/fluids/light_oil_still", "thermal:block/fluids/light_oil_flow");
    }

    protected LightOilFluid(String key, String stillTexture, String flowTexture) {

        super(FLUIDS, key, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .density(850)
                .viscosity(1400)
                .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
        );

        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ThermalItemGroups.THERMAL_ITEMS)));
        properties.bucket(bucket);
    }

}

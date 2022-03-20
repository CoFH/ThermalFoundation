package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidAttributes;

import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.ID_FLUID_SAP;

public class SapFluid extends FluidCoFH {

    public static SapFluid create() {

        return new SapFluid(ID_FLUID_SAP, "thermal:block/fluids/sap_still", "thermal:block/fluids/sap_flow");
    }

    protected SapFluid(String key, String stillTexture, String flowTexture) {

        super(FLUIDS, key, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .density(1050)
                .viscosity(1500)
                .sound(SoundEvents.BOTTLE_FILL, SoundEvents.BOTTLE_EMPTY)
        );

        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThermalItemGroups.THERMAL_ITEMS)));
        properties.bucket(bucket);
    }

}

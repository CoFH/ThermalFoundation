package cofh.thermal.core.fluid;

import cofh.lib.content.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.IFluidTypeRenderProperties;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.*;
import static cofh.thermal.lib.common.ThermalIDs.ID_FLUID_SYRUP;

public class SyrupFluid extends FluidCoFH {

    private static SyrupFluid INSTANCE;

    public static SyrupFluid create() {

        if (INSTANCE == null) {
            INSTANCE = new SyrupFluid();
        }
        return INSTANCE;
    }

    protected SyrupFluid() {

        super(FLUIDS, ID_FLUID_SYRUP);

        bucket = ITEMS.register(bucket(ID_FLUID_SYRUP), () -> new BucketItem(stillFluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThermalItemGroups.THERMAL_ITEMS)));
    }

    @Override
    protected ForgeFlowingFluid.Properties fluidProperties() {

        return new ForgeFlowingFluid.Properties(type(), stillFluid, flowingFluid).bucket(bucket);
    }

    @Override
    protected Supplier<FluidType> type() {

        return TYPE;
    }

    public static final RegistryObject<FluidType> TYPE = FLUID_TYPES.register(ID_FLUID_SYRUP, () -> new FluidType(FluidType.Properties.create()
            .density(1400)
            .viscosity(2500)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BOTTLE_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BOTTLE_EMPTY)) {

        @Override
        public void initializeClient(Consumer<IFluidTypeRenderProperties> consumer) {

            consumer.accept(new IFluidTypeRenderProperties() {

                private static final ResourceLocation
                        STILL = new ResourceLocation("thermal:block/fluids/syrup_still"),
                        FLOW = new ResourceLocation("thermal:block/fluids/syrup_flow");

                @Override
                public ResourceLocation getStillTexture() {

                    return STILL;
                }

                @Override
                public ResourceLocation getFlowingTexture() {

                    return FLOW;
                }

            });
        }
    });

}

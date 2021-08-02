package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.ID_FLUID_GLOWSTONE;

public class GlowstoneFluid extends FluidCoFH {

    public static GlowstoneFluid create() {

        return new GlowstoneFluid(ID_FLUID_GLOWSTONE, "thermal:block/fluids/glowstone_still", "thermal:block/fluids/glowstone_flow");
    }

    protected GlowstoneFluid(String key, String stillTexture, String flowTexture) {

        stillFluid = FLUIDS.register(key, () -> new ForgeFlowingFluid.Source(properties));
        flowingFluid = FLUIDS.register(flowing(key), () -> new ForgeFlowingFluid.Flowing(properties));

        // block = BLOCKS.register(key, () -> new GlowstoneFluidBlock(stillFluid, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ThermalItemGroups.THERMAL_ITEMS).rarity(Rarity.UNCOMMON)));

        properties = new ForgeFlowingFluid.Properties(stillFluid, flowingFluid, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .luminosity(15)
                .density(-500)
                .viscosity(100)
                .rarity(Rarity.UNCOMMON)
                .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
        ).bucket(bucket);//.block(block).levelDecreasePerBlock(4);
    }

    public static class GlowstoneFluidBlock extends FlowingFluidBlock {

        public GlowstoneFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {

            super(supplier, properties);
        }

    }

}

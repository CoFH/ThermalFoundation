package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.lib.util.helpers.MathHelper;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.core.init.TCoreIDs.ID_FLUID_REDSTONE;

public class RedstoneFluid extends FluidCoFH {

    protected static boolean signal = true;

    public static RedstoneFluid create() {

        return new RedstoneFluid(ID_FLUID_REDSTONE, "thermal:block/fluids/redstone_still", "thermal:block/fluids/redstone_flow");
    }

    protected RedstoneFluid(String key, String stillTexture, String flowTexture) {

        stillFluid = FLUIDS.register(key, () -> new ForgeFlowingFluid.Source(properties));
        flowingFluid = FLUIDS.register(flowing(key), () -> new ForgeFlowingFluid.Flowing(properties));

        // block = BLOCKS.register(key, () -> new RedstoneFluidBlock(stillFluid, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ThermalItemGroups.THERMAL_ITEMS).rarity(Rarity.UNCOMMON)));

        properties = new ForgeFlowingFluid.Properties(stillFluid, flowingFluid, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .luminosity(7)
                .density(1200)
                .viscosity(1500)
                .rarity(Rarity.UNCOMMON)
                .sound(SoundEvents.ITEM_BUCKET_FILL, SoundEvents.ITEM_BUCKET_EMPTY)
        ).bucket(bucket);//.block(block);
    }

    public static class RedstoneFluidBlock extends FlowingFluidBlock {

        public RedstoneFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {

            super(supplier, properties);
        }

        @Override
        public boolean canProvidePower(BlockState state) {

            return signal;
        }

        @Override
        public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {

            return signal ? MathHelper.clamp(blockState.get(LEVEL) * 2 - 1, 1, 15) : 0;
        }

    }

}

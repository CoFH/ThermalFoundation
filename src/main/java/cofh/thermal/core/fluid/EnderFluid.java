package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.lib.util.Utils;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.Supplier;

import static cofh.lib.util.references.CoreReferences.ENDERFERENCE;
import static cofh.thermal.core.ThermalCore.FLUIDS;
import static cofh.thermal.core.ThermalCore.ITEMS;
import static cofh.thermal.lib.common.ThermalIDs.ID_FLUID_ENDER;

public class EnderFluid extends FluidCoFH {

    protected static boolean teleport = true;
    protected static int duration = 100;

    public static EnderFluid create() {

        return new EnderFluid(ID_FLUID_ENDER, "thermal:block/fluids/ender_still", "thermal:block/fluids/ender_flow");
    }

    protected EnderFluid(String key, String stillTexture, String flowTexture) {

        stillFluid = FLUIDS.register(key, () -> new ForgeFlowingFluid.Source(properties));
        flowingFluid = FLUIDS.register(flowing(key), () -> new ForgeFlowingFluid.Flowing(properties));

        // block = BLOCKS.register(key, () -> new EnderFluidBlock(stillFluid, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
        bucket = ITEMS.register(bucket(key), () -> new BucketItem(stillFluid, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(ThermalItemGroups.THERMAL_ITEMS).rarity(Rarity.UNCOMMON)));

        properties = new ForgeFlowingFluid.Properties(stillFluid, flowingFluid, FluidAttributes.builder(new ResourceLocation(stillTexture), new ResourceLocation(flowTexture))
                .luminosity(3)
                .density(4000)
                .viscosity(2500)
                .rarity(Rarity.UNCOMMON)
                .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY)
        ).bucket(bucket);//.block(block).levelDecreasePerBlock(2);
    }

    public static class EnderFluidBlock extends FlowingFluidBlock {

        public EnderFluidBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {

            super(supplier, properties);
        }

        @Override
        public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {

            if (!teleport || Utils.isClientWorld(worldIn)) {
                return;
            }
            if (entityIn instanceof ItemEntity || entityIn instanceof ExperienceOrbEntity) {
                return;
            }
            BlockPos randPos = pos.offset(-8 + worldIn.random.nextInt(17), worldIn.random.nextInt(8), -8 + worldIn.random.nextInt(17));

            if (!worldIn.getBlockState(randPos).getMaterial().isSolid()) {
                if (entityIn instanceof LivingEntity) {
                    if (Utils.teleportEntityTo(entityIn, randPos)) {
                        ((LivingEntity) entityIn).addEffect(new EffectInstance(ENDERFERENCE, duration, 0, false, false));
                    }
                } else if (worldIn.getGameTime() % duration == 0) {
                    entityIn.setPos(randPos.getX(), randPos.getY(), randPos.getZ());
                    entityIn.playSound(SoundEvents.ENDERMAN_TELEPORT, 1.0F, 1.0F);
                }
            }
        }

    }

}

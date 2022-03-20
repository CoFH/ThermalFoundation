package cofh.thermal.core.fluid;

import cofh.lib.fluid.FluidCoFH;
import cofh.thermal.lib.common.ThermalItemGroups;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

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

/*    public static class EnderFluidBlock extends FlowingFluidBlock {

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

    }*/

}

package cofh.thermal.core.item;

import cofh.core.content.item.ItemCoFH;
import cofh.lib.util.Utils;
import cofh.thermal.core.entity.projectile.BlitzProjectile;
import cofh.thermal.core.init.TCoreSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import static cofh.core.init.CoreMobEffects.LIGHTNING_RESISTANCE;

public class LightningChargeItem extends ItemCoFH {

    public LightningChargeItem(Properties builder) {

        super(builder);

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());

        //        if (player != null && (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, context.getClickedFace(), context.getItemInHand()))) {
        //            return ActionResultType.FAIL;
        //        }
        if (world.canSeeSky(pos)) {

            if (world instanceof ServerLevel) {
                if (player != null) {
                    player.addEffect(new MobEffectInstance(LIGHTNING_RESISTANCE.get(), 20, 0, false, false, false));
                }
                Utils.spawnLightningBolt(world, pos, player);
                // world.addFreshEntity(new ElectricArcEntity(world, Vec3.atBottomCenterOf(pos)).setOwner(player));
            }
            playUseSound(world, pos);
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private void playUseSound(Level worldIn, BlockPos pos) {

        worldIn.playSound(null, pos, TCoreSounds.SOUND_BLITZ_SHOOT, SoundSource.BLOCKS, 1.0F, (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.2F + 1.0F);
    }

    // region DISPENSER BEHAVIOR
    private static final DispenseItemBehavior DISPENSER_BEHAVIOR = new DefaultDispenseItemBehavior() {

        @Override
        public ItemStack execute(BlockSource source, ItemStack stack) {

            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            Position iposition = DispenserBlock.getDispensePosition(source);
            double d0 = iposition.x() + (double) ((float) direction.getStepX() * 0.3F);
            double d1 = iposition.y() + (double) ((float) direction.getStepY() * 0.3F);
            double d2 = iposition.z() + (double) ((float) direction.getStepZ() * 0.3F);
            Level world = source.getLevel();
            double d3 = world.random.nextGaussian() * 0.05D + (double) direction.getStepX();
            double d4 = world.random.nextGaussian() * 0.05D + (double) direction.getStepY();
            double d5 = world.random.nextGaussian() * 0.05D + (double) direction.getStepZ();
            world.addFreshEntity(new BlitzProjectile(d0, d1, d2, d3, d4, d5, world));
            stack.shrink(1);
            return stack;
        }

        @Override
        protected void playSound(BlockSource source) {

            source.getLevel().levelEvent(1018, source.getPos(), 0);
        }
    };
    // endregion
}

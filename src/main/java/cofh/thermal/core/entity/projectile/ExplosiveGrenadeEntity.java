package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.Utils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import static cofh.thermal.core.init.TCoreReferences.EXPLOSIVE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.EXPLOSIVE_GRENADE_ITEM;

public class ExplosiveGrenadeEntity extends AbstractGrenadeEntity {

    public static double explosionStrength = 1.9;
    public static boolean explosionsBreakBlocks = true;

    public ExplosiveGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public ExplosiveGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(EXPLOSIVE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public ExplosiveGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(EXPLOSIVE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return EXPLOSIVE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) explosionStrength, false, explosionsBreakBlocks ? Explosion.BlockInteraction.BREAK : Explosion.BlockInteraction.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}

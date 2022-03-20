package cofh.thermal.core.entity.projectile;

import cofh.core.network.packet.client.PlayerMotionPacket;
import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.references.CoreReferences.SLIMED;
import static cofh.thermal.core.init.TCoreReferences.SLIME_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.SLIME_GRENADE_ITEM;

public class SlimeGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 600;

    public SlimeGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public SlimeGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(SLIME_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public SlimeGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(SLIME_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return SLIME_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            if (!this.isInWater()) {
                affectNearbyEntities(this, level, this.blockPosition(), radius, getOwner());
                makeAreaOfEffectCloud();
            }
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloud cloud = new AreaEffectCloud(level, getX(), getY(), getZ());
        cloud.setRadius(1);
        cloud.setParticle(ParticleTypes.ITEM_SLIME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        level.addFreshEntity(cloud);
    }

    public static void affectNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> mobs = worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE);

        for (LivingEntity mob : mobs) {
            mob.addEffect(new MobEffectInstance(SLIMED, effectDuration, 0, false, true));

            double d5 = mob.getX() - entity.getX();
            double d7 = mob.getY() - entity.getY();
            double d9 = mob.getZ() - entity.getZ();
            double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

            if (d13 != 0.0D) {
                d5 = d5 / d13;
                d7 = d7 / d13;
                d9 = d9 / d13;
                double d12 = Math.sqrt(entity.distanceToSqr(mob) / 32.0D);
                double d14 = Explosion.getSeenPercent(entity.position(), mob);
                double d11 = (radius - d12) * d14;
                d11 *= (1.0D - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                if (mob instanceof ServerPlayer player) {
                    d11 /= 4.0D;
                    PlayerMotionPacket.sendToClient(d5 * d11, d7 * d11, d9 * d11, player);
                } else {
                    mob.setDeltaMovement(mob.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                }
            }
        }
    }

}

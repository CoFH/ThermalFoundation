package cofh.thermal.core.entity.projectile;

import cofh.core.network.packet.client.PlayerMotionPacket;
import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.references.CoreReferences.SLIMED;
import static cofh.thermal.core.init.TCoreReferences.SLIME_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.SLIME_GRENADE_ITEM;

public class SlimeGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 600;

    public SlimeGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public SlimeGrenadeEntity(World worldIn, double x, double y, double z) {

        super(SLIME_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public SlimeGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(SLIME_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return SLIME_GRENADE_ITEM;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (Utils.isServerWorld(world)) {
            if (!this.isInWater()) {
                affectNearbyEntities(this, world, this.getPosition(), radius, func_234616_v_());
                makeAreaOfEffectCloud();
            }
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

    private void makeAreaOfEffectCloud() {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(world, getPosX(), getPosY(), getPosZ());
        cloud.setRadius(1);
        cloud.setParticleData(ParticleTypes.ITEM_SLIME);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

    public static void affectNearbyEntities(Entity entity, World worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> mobs = worldIn.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE);

        for (LivingEntity mob : mobs) {
            mob.addPotionEffect(new EffectInstance(SLIMED, effectDuration, 0, false, true));

            double d5 = mob.getPosX() - entity.getPosX();
            double d7 = mob.getPosY() - entity.getPosY();
            double d9 = mob.getPosZ() - entity.getPosZ();
            double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

            if (d13 != 0.0D) {
                d5 = d5 / d13;
                d7 = d7 / d13;
                d9 = d9 / d13;
                double d12 = Math.sqrt(entity.getDistanceSq(mob) / 32.0D);
                double d14 = Explosion.getBlockDensity(entity.getPositionVec(), mob);
                double d11 = (radius - d12) * d14;
                d11 *= (1.0D - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                if (mob instanceof ServerPlayerEntity) {
                    d11 /= 4.0D;
                    PlayerMotionPacket.sendToClient(d5 * d11, d7 * d11, d9 * d11, (ServerPlayerEntity) mob);
                } else {
                    mob.setMotion(mob.getMotion().add(d5 * d11, d7 * d11, d9 * d11));
                }
            }
        }
    }

}

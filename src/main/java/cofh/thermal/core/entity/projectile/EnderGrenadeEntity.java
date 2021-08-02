package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static cofh.lib.util.references.CoreReferences.ENDERFERENCE;
import static cofh.thermal.core.init.TCoreReferences.ENDER_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.ENDER_GRENADE_ITEM;

public class EnderGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectDuration = 300;

    public EnderGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public EnderGrenadeEntity(World worldIn, double x, double y, double z) {

        super(ENDER_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public EnderGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(ENDER_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return ENDER_GRENADE_ITEM;
    }

    @Override
    protected void onImpact(RayTraceResult result) {

        if (Utils.isServerWorld(world)) {
            if (!this.isInWater()) {
                affectNearbyEntities(this, world, this.getPosition(), radius, func_234616_v_());
                AreaUtils.transformEnderAir(this, world, this.getPosition(), radius);
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
        cloud.setParticleData(ParticleTypes.PORTAL);
        cloud.setDuration(CLOUD_DURATION);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());

        world.addEntity(cloud);
    }

    public static void affectNearbyEntities(Entity entity, World worldIn, BlockPos pos, int radius, @Nullable Entity source) {

        AxisAlignedBB area = new AxisAlignedBB(pos.add(-radius, -radius, -radius), pos.add(1 + radius, 1 + radius, 1 + radius));
        List<LivingEntity> mobs = worldIn.getEntitiesWithinAABB(LivingEntity.class, area, EntityPredicates.IS_ALIVE);

        for (LivingEntity mob : mobs) {
            if (mob instanceof EndermanEntity || mob instanceof EndermiteEntity) {
                mob.addPotionEffect(new EffectInstance(ENDERFERENCE, effectDuration, 0, false, true));
                mob.attackEntityFrom(DamageSource.causeExplosionDamage(source instanceof LivingEntity ? (LivingEntity) source : null), 4.0F);
            }
        }
    }

}

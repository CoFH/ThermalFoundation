package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;

import static cofh.lib.util.references.CoreReferences.SHOCKED;
import static cofh.thermal.core.init.TCoreReferences.LIGHTNING_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.LIGHTNING_GRENADE_ITEM;

public class LightningGrenadeEntity extends AbstractGrenadeEntity {

    public static int effectAmplifier = 1;
    public static int effectDuration = 300;

    public LightningGrenadeEntity(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {

        super(type, worldIn);
    }

    public LightningGrenadeEntity(Level worldIn, double x, double y, double z) {

        super(LIGHTNING_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public LightningGrenadeEntity(Level worldIn, LivingEntity livingEntityIn) {

        super(LIGHTNING_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return LIGHTNING_GRENADE_ITEM;
    }

    @Override
    protected void onHit(HitResult result) {

        if (Utils.isServerWorld(level)) {
            BlockPos pos = this.blockPosition();
            if (this.level.canSeeSky(pos)) {
                Utils.spawnLightningBolt(level, pos, getOwner());
            }
            shockNearbyEntities(this, level, this.blockPosition(), radius);
            AreaUtils.zapNearbyGround(this, level, this.blockPosition(), radius, 0.05, 3);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
        if (result.getType() == HitResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

    public static void shockNearbyEntities(Entity entity, Level worldIn, BlockPos pos, int radius) {

        AABB area = new AABB(pos.offset(-radius, -radius, -radius), pos.offset(1 + radius, 1 + radius, 1 + radius));
        worldIn.getEntitiesOfClass(LivingEntity.class, area, EntitySelector.ENTITY_STILL_ALIVE)
                .forEach(livingEntity -> livingEntity.addEffect(new MobEffectInstance(SHOCKED, effectDuration, effectAmplifier, false, false)));
    }

}

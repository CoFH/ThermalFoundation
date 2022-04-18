package cofh.thermal.core.entity.explosive;

import cofh.core.network.packet.client.PlayerMotionPacket;
import cofh.lib.entity.*;
import cofh.lib.util.AreaUtils;
import cofh.lib.util.Utils;
import cofh.lib.util.references.CoreReferences;
import cofh.thermal.core.item.PhytoGroItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

import static cofh.lib.util.references.CoreReferences.SLIMED;
import static net.minecraft.potion.Effects.WITHER;

public class DetonateUtils {

    // region RENDERING REGISTRATION
    public static List<RegistryObject<EntityType<? extends AbstractGrenadeEntity>>> GRENADES = new LinkedList<>();
    public static List<RegistryObject<EntityType<? extends AbstractTNTEntity>>> TNT = new LinkedList<>();
    public static List<RegistryObject<EntityType<? extends AbstractTNTMinecartEntity>>> CARTS = new LinkedList<>();
    // endregion

    // region HELPERS
    public static void makeAreaOfEffectCloud(World level, IParticleData particle, Vector3d pos, float radius, int duration) {

        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(level, pos.x, pos.y, pos.z);
        cloud.setRadius(1);
        cloud.setParticle(particle);
        cloud.setDuration(duration);
        cloud.setWaitTime(0);
        cloud.setRadiusPerTick((radius - cloud.getRadius()) / (float) cloud.getDuration());
        level.addFreshEntity(cloud);
    }

    public static void makeAreaOfEffectCloud(World level, IParticleData particle, Vector3d pos, float radius) {

        makeAreaOfEffectCloud(level, particle, pos, radius, 20);
    }
    // endregion

    // region DETONATE ACTIONS
    public static void fire(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        if (!explosive.isInWater()) {
            AreaUtils.igniteEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
            AreaUtils.fireTransformSpecial.transformSphere(level, pos, radius, 0.6F, owner);
            AreaUtils.fireTransform.transformSphere(level, pos, radius, 0.2F, owner);
            makeAreaOfEffectCloud(level, ParticleTypes.FLAME, pos, radius);
        }
    }

    public static void ice(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        AreaUtils.iceTransform.transformSphere(level, pos, radius, owner);
        AreaUtils.chillEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
        makeAreaOfEffectCloud(level, CoreReferences.FROST_PARTICLE, pos, radius);
    }

    public static void earth(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        AreaUtils.earthTransform.transformSphere(level, pos, radius, owner);
        AreaUtils.sunderEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
    }

    public static void lightning(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        BlockPos blockPos = new BlockPos(pos);
        if (level.canSeeSky(blockPos)) {
            Utils.spawnLightningBolt(level, blockPos, owner);
        }
        AreaUtils.shockEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
        level.addFreshEntity(new ElectricFieldEntity(level, pos, radius, 100).setOwner(owner instanceof LivingEntity ? (LivingEntity) owner : null));
    }

    public static void ender(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        if (!explosive.isInWater()) {
            AreaUtils.enderAirTransform.transformSphere(level, pos, radius, owner);
            AreaUtils.enderfereEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
            makeAreaOfEffectCloud(level, ParticleTypes.PORTAL, pos, radius);
        }
    }

    public static void glow(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        AreaUtils.glowAirTransform.transformSphere(level, pos, radius, explosive);
        AreaUtils.glowEntities.applyEffectNearby(level, pos, radius, duration, amplifier);
        makeAreaOfEffectCloud(level, ParticleTypes.INSTANT_EFFECT, pos, radius);
    }

    public static void redstone(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        AreaUtils.signalAirTransform.transformSphere(level, pos, radius, owner);
        makeAreaOfEffectCloud(level, RedstoneParticleData.REDSTONE, pos, radius);
    }

    public static void slime(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        BlockPos blockPos = new BlockPos(pos);
        AxisAlignedBB area = new AxisAlignedBB(blockPos.offset(-radius, -radius, -radius), blockPos.offset(1 + radius, 1 + radius, 1 + radius));

        for (LivingEntity mob : level.getEntitiesOfClass(LivingEntity.class, area, EntityPredicates.ENTITY_STILL_ALIVE)) {
            mob.addEffect(new EffectInstance(SLIMED, duration, amplifier, false, true));

            double d5 = mob.getX() - explosive.getX();
            double d7 = mob.getY() - explosive.getY();
            double d9 = mob.getZ() - explosive.getZ();
            double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

            if (d13 != 0.0D) {
                d5 = d5 / d13;
                d7 = d7 / d13;
                d9 = d9 / d13;
                double d12 = Math.sqrt(explosive.distanceToSqr(mob) / 32.0D);
                double d14 = Explosion.getSeenPercent(explosive.position(), mob);
                double d11 = (radius - d12) * d14;
                d11 *= (1.0D - mob.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                if (mob instanceof ServerPlayerEntity) {
                    d11 /= 4.0D;
                    PlayerMotionPacket.sendToClient(d5 * d11, d7 * d11, d9 * d11, (ServerPlayerEntity) mob);
                } else {
                    mob.setDeltaMovement(mob.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                }
            }
        }
        makeAreaOfEffectCloud(level, ParticleTypes.ITEM_SLIME, pos, radius);
    }

    public static void phyto(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        AreaUtils.growPlants.transformSphere(level, pos, radius, owner);
        for (int i = 0; i < 2; ++i) {
            PhytoGroItem.growSeagrass(level, explosive.blockPosition(), null);
        }
        makeAreaOfEffectCloud(level, ParticleTypes.HAPPY_VILLAGER, pos, radius);
    }

    public static void explosive(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        boolean explosionsBreakBlocks = true; //TODO: config
        level.explode(explosive, explosive.getX(), explosive.getY(), explosive.getZ(), 1.9F, false, explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
    }

    public static void nuke(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        boolean explosionsBreakBlocks = true; //TODO: config
        level.setBlockAndUpdate(explosive.blockPosition(), Blocks.AIR.defaultBlockState());
        if (radius <= 0) {
            return;
        }

        // ENTITIES
        float entityRadius = radius * 3;
        BlockPos blockPos = new BlockPos(pos);
        AxisAlignedBB area = new AxisAlignedBB(blockPos.offset(-entityRadius, -entityRadius, -entityRadius), blockPos.offset(1 + entityRadius, 1 + entityRadius, 1 + entityRadius));
        double entityRadiusSqr = entityRadius * entityRadius;
        level.getEntitiesOfClass(LivingEntity.class, area, EntityPredicates.ENTITY_STILL_ALIVE)
                .forEach(target -> {
                    double distSqr = pos.distanceToSqr(target.position());
                    if (distSqr < entityRadiusSqr) {
                        float damage = (float) MathHelper.clamp(entityRadiusSqr - distSqr, entityRadius, entityRadiusSqr);
                        target.hurt(DamageSource.explosion(owner instanceof LivingEntity ? (LivingEntity) owner : null), damage);
                        target.addEffect(new EffectInstance(WITHER, duration, amplifier, false, false));
                    }
                });

        // BLOCKS
        float blockRadius = radius * 2;
        float f = Math.min(AreaUtils.HORZ_MAX, blockRadius);
        float maxResistance = 400F * blockRadius * blockRadius;
        float f2 = f * f;

        for (BlockPos iterPos : BlockPos.betweenClosed(blockPos.offset(-f, -f / 2, -f), blockPos.offset(f, f, f))) {
            double distance = iterPos.distSqr(explosive.position(), true);
            if (distance < f2) {
                BlockState state = level.getBlockState(iterPos);
                if (!state.isAir(level, iterPos) && state.getBlock().getExplosionResistance(state, level, iterPos, null) < maxResistance - (maxResistance * distance / f2)) {
                    level.setBlockAndUpdate(iterPos, Blocks.AIR.defaultBlockState());
                }
            }
        }
        level.explode(explosive, explosive.getX(), explosive.getY(), explosive.getZ(), radius * 0.38F, true, explosionsBreakBlocks ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
    }

    public static void gravity(World level, Entity explosive, @Nullable Entity owner, Vector3d pos, float radius, int duration, int amplifier) {

        level.addFreshEntity((new BlackHoleEntity(level, pos, radius)).setOwner(owner instanceof LivingEntity ? (LivingEntity) owner : null));
        //TODO: particle
    }
    // endregion

}

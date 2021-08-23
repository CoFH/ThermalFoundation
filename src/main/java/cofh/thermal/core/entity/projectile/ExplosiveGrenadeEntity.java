package cofh.thermal.core.entity.projectile;

import cofh.lib.entity.AbstractGrenadeEntity;
import cofh.lib.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import static cofh.thermal.core.init.TCoreReferences.EXPLOSIVE_GRENADE_ENTITY;
import static cofh.thermal.core.init.TCoreReferences.EXPLOSIVE_GRENADE_ITEM;

public class ExplosiveGrenadeEntity extends AbstractGrenadeEntity {

    public static double explosionStrength = 1.9;
    public static boolean explosionsBreakBlocks = true;

    public ExplosiveGrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {

        super(type, worldIn);
    }

    public ExplosiveGrenadeEntity(World worldIn, double x, double y, double z) {

        super(EXPLOSIVE_GRENADE_ENTITY, x, y, z, worldIn);
    }

    public ExplosiveGrenadeEntity(World worldIn, LivingEntity livingEntityIn) {

        super(EXPLOSIVE_GRENADE_ENTITY, livingEntityIn, worldIn);
    }

    @Override
    protected Item getDefaultItem() {

        return EXPLOSIVE_GRENADE_ITEM;
    }

    @Override
    protected void onHit(RayTraceResult result) {

        if (Utils.isServerWorld(level)) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), (float) explosionStrength, false, explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.tickCount < 10) {
            return;
        }
        this.level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1.0D, 0.0D, 0.0D);
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
    }

}

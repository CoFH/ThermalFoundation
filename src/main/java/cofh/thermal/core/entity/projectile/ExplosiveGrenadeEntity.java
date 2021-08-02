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
    protected void onImpact(RayTraceResult result) {

        if (Utils.isServerWorld(world)) {
            world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), (float) explosionStrength, false, explosionsBreakBlocks ? Explosion.Mode.BREAK : Explosion.Mode.NONE);
            this.world.setEntityState(this, (byte) 3);
            this.remove();
        }
        if (result.getType() == RayTraceResult.Type.ENTITY && this.ticksExisted < 10) {
            return;
        }
        this.world.addParticle(ParticleTypes.EXPLOSION, this.getPosX(), this.getPosY(), this.getPosZ(), 1.0D, 0.0D, 0.0D);
        this.world.playSound(this.getPosX(), this.getPosY(), this.getPosZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 0.5F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
    }

}

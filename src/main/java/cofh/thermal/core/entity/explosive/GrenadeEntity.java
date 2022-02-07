package cofh.thermal.core.entity.explosive;

import cofh.lib.block.IDetonatable;
import cofh.lib.entity.AbstractGrenadeEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static cofh.thermal.core.ThermalCore.ITEMS;

public class GrenadeEntity extends AbstractGrenadeEntity implements IDetonatable {

    protected String id;
    protected IDetonateAction detonateAction;

    public GrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn, String id, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        this.id = id;
    }

    public GrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn, String id, IDetonateAction detonateAction, double x, double y, double z) {

        super(type, x, y, z, worldIn);
        this.detonateAction = detonateAction;
        this.id = id;
    }

    public GrenadeEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn, String id, IDetonateAction detonateAction, LivingEntity livingEntityIn) {

        super(type, livingEntityIn, worldIn);
        this.detonateAction = detonateAction;
        this.id = id;
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, getOwner(), pos, this.radius, this.effectDuration, this.effectAmplifier);
        this.remove();
    }

    @Override
    protected Item getDefaultItem() {

        return ITEMS.get(id);
    }
}

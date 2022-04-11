package cofh.thermal.core.entity.explosive;

import cofh.lib.block.IDetonatable;
import cofh.lib.entity.AbstractGrenadeEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static cofh.thermal.core.ThermalCore.ITEMS;

public class GrenadeEntity extends AbstractGrenadeEntity implements IDetonatable {

    protected Item item;
    protected IDetonateAction detonateAction;

    public GrenadeEntity(EntityType<? extends AbstractGrenadeEntity> type, World worldIn, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(type.getRegistryName());
    }

    public GrenadeEntity(EntityType<? extends AbstractGrenadeEntity> type, World worldIn, IDetonateAction detonateAction, double x, double y, double z) {

        super(type, x, y, z, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(type.getRegistryName());
    }

    public GrenadeEntity(EntityType<? extends AbstractGrenadeEntity> type, World worldIn, IDetonateAction detonateAction, LivingEntity livingEntityIn) {

        super(type, livingEntityIn, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(type.getRegistryName());
    }

    @Override
    public void detonate(Vector3d pos) {

        detonateAction.detonate(level, this, getOwner(), pos, this.radius, this.effectDuration, this.effectAmplifier);
    }

    @Override
    protected Item getDefaultItem() {

        return item;
    }
}

package cofh.thermal.core.entity.explosive;

import cofh.core.entity.AbstractGrenade;
import cofh.lib.api.IDetonatable;
import cofh.lib.util.Utils;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static cofh.thermal.core.ThermalCore.ITEMS;

public class Grenade extends AbstractGrenade implements IDetonatable {

    protected Item item;
    protected IDetonateAction detonateAction;

    public Grenade(EntityType<? extends AbstractGrenade> type, Level worldIn, IDetonateAction detonateAction) {

        super(type, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(Utils.getRegistryName(type));
    }

    public Grenade(EntityType<? extends AbstractGrenade> type, Level worldIn, IDetonateAction detonateAction, double x, double y, double z) {

        super(type, x, y, z, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(Utils.getRegistryName(type));
    }

    public Grenade(EntityType<? extends AbstractGrenade> type, Level worldIn, IDetonateAction detonateAction, LivingEntity livingEntityIn) {

        super(type, livingEntityIn, worldIn);
        this.detonateAction = detonateAction;
        item = ITEMS.get(Utils.getRegistryName(type));
    }

    @Override
    public void detonate(Vec3 pos) {

        detonateAction.detonate(level, this, getOwner(), pos, this.radius, this.effectDuration, this.effectAmplifier);
    }

    @Override
    protected Item getDefaultItem() {

        return item;
    }

}

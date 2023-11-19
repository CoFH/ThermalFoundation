package cofh.thermal.foundation.init;

import cofh.core.entity.BoatCoFH;
import cofh.core.entity.ChestBoatCoFH;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraftforge.registries.RegistryObject;

import static cofh.thermal.core.ThermalCore.ENTITIES;
import static cofh.thermal.foundation.init.TFndIDs.ID_RUBBERWOOD_BOAT;
import static cofh.thermal.foundation.init.TFndIDs.ID_RUBBERWOOD_CHEST_BOAT;

public class TFndEntities {

    private TFndEntities() {

    }

    public static void register() {

    }

    public static final RegistryObject<EntityType<? extends Boat>> RUBBERWOOD_BOAT = ENTITIES.register(ID_RUBBERWOOD_BOAT, () -> EntityType.Builder.<BoatCoFH>of(BoatCoFH::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(ID_RUBBERWOOD_BOAT));
    public static final RegistryObject<EntityType<? extends Boat>> RUBBERWOOD_CHEST_BOAT = ENTITIES.register(ID_RUBBERWOOD_CHEST_BOAT, () -> EntityType.Builder.<ChestBoatCoFH>of(ChestBoatCoFH::new, MobCategory.MISC).sized(1.375F, 0.5625F).clientTrackingRange(10).build(ID_RUBBERWOOD_CHEST_BOAT));

}

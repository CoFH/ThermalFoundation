package cofh.thermal.core.init;

import cofh.thermal.core.entity.explosive.DetonateUtil;
import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.entity.monster.BlitzEntity;
import cofh.thermal.core.entity.monster.BlizzEntity;
import cofh.thermal.core.entity.projectile.BasalzProjectileEntity;
import cofh.thermal.core.entity.projectile.BlitzProjectileEntity;
import cofh.thermal.core.entity.projectile.BlizzProjectileEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;

import static cofh.thermal.core.ThermalCore.ENTITIES;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.core.util.RegistrationHelper.registerExplosives;
import static cofh.thermal.core.util.RegistrationHelper.registerGrenade;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreEntities {

    private TCoreEntities() {

    }

    public static void register() {

        ENTITIES.register(ID_BASALZ, () -> EntityType.Builder.of(BasalzEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).fireImmune().build(ID_BASALZ));
        ENTITIES.register(ID_BLIZZ, () -> EntityType.Builder.of(BlizzEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).build(ID_BLIZZ));
        ENTITIES.register(ID_BLITZ, () -> EntityType.Builder.of(BlitzEntity::new, EntityClassification.MONSTER).sized(0.6F, 1.8F).build(ID_BLITZ));

        ENTITIES.register(ID_BASALZ_PROJECTILE, () -> EntityType.Builder.<BasalzProjectileEntity>of(BasalzProjectileEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).build(ID_BASALZ_PROJECTILE));
        ENTITIES.register(ID_BLIZZ_PROJECTILE, () -> EntityType.Builder.<BlizzProjectileEntity>of(BlizzProjectileEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).build(ID_BLIZZ_PROJECTILE));
        ENTITIES.register(ID_BLITZ_PROJECTILE, () -> EntityType.Builder.<BlitzProjectileEntity>of(BlitzProjectileEntity::new, EntityClassification.MISC).sized(0.3125F, 0.3125F).build(ID_BLITZ_PROJECTILE));

        registerGrenade("explosive_grenade", DetonateUtil::explosive);

        registerExplosives("fire", DetonateUtil::fire);
        registerExplosives("ice", DetonateUtil::ice);
        registerExplosives("lightning", DetonateUtil::lightning);
        registerExplosives("earth", DetonateUtil::earth);

        registerExplosives("ender", DetonateUtil::ender);
        registerExplosives("glowstone", DetonateUtil::glow);
        registerExplosives("redstone", DetonateUtil::redstone);
        registerExplosives("slime", DetonateUtil::slime);

        registerExplosives("phyto", DetonateUtil::phyto);
        registerExplosives("nuke", DetonateUtil::nuke);
        //registerExplosives("gravity", DetonateUtil::gravity);
    }

    public static void setup() {

        EntitySpawnPlacementRegistry.register(BASALZ_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BasalzEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(BLITZ_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlitzEntity::canSpawn);
        EntitySpawnPlacementRegistry.register(BLIZZ_ENTITY, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, BlizzEntity::canSpawn);
    }

}

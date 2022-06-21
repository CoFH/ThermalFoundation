package cofh.thermal.core.init;

import cofh.thermal.core.entity.explosive.DetonateUtils;
import cofh.thermal.core.entity.monster.Basalz;
import cofh.thermal.core.entity.monster.Blitz;
import cofh.thermal.core.entity.monster.Blizz;
import cofh.thermal.core.entity.projectile.BasalzProjectile;
import cofh.thermal.core.entity.projectile.BlitzProjectile;
import cofh.thermal.core.entity.projectile.BlizzProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.RegistryObject;

import static cofh.thermal.core.ThermalCore.ENTITIES;
import static cofh.thermal.core.util.RegistrationHelper.registerGrenade;
import static cofh.thermal.core.util.RegistrationHelper.registerTNT;
import static cofh.thermal.lib.common.ThermalFlags.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreEntities {

    private TCoreEntities() {

    }

    public static void register() {

        registerGrenade(ID_EXPLOSIVE_GRENADE, DetonateUtils::explosive, getFlag(FLAG_BASIC_EXPLOSIVES));

        registerGrenade(ID_FIRE_GRENADE, DetonateUtils::fire, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerGrenade(ID_ICE_GRENADE, DetonateUtils::ice, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerGrenade(ID_LIGHTNING_GRENADE, DetonateUtils::lightning, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerGrenade(ID_EARTH_GRENADE, DetonateUtils::earth, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));

        registerGrenade(ID_ENDER_GRENADE, DetonateUtils::ender, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerGrenade(ID_GLOWSTONE_GRENADE, DetonateUtils::glow, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerGrenade(ID_REDSTONE_GRENADE, DetonateUtils::redstone, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerGrenade(ID_SLIME_GRENADE, DetonateUtils::slime, getFlag(FLAG_BASIC_EXPLOSIVES));

        registerGrenade(ID_PHYTO_GRENADE, DetonateUtils::phyto, getFlag(FLAG_PHYTOGRO_EXPLOSIVES));
        registerGrenade(ID_NUKE_GRENADE, DetonateUtils::nuke, getFlag(FLAG_NUCLEAR_EXPLOSIVES));

        registerTNT(ID_FIRE_TNT, DetonateUtils::fire, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerTNT(ID_ICE_TNT, DetonateUtils::ice, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerTNT(ID_LIGHTNING_TNT, DetonateUtils::lightning, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));
        registerTNT(ID_EARTH_TNT, DetonateUtils::earth, getFlag(FLAG_ELEMENTAL_EXPLOSIVES));

        registerTNT(ID_ENDER_TNT, DetonateUtils::ender, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerTNT(ID_GLOWSTONE_TNT, DetonateUtils::glow, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerTNT(ID_REDSTONE_TNT, DetonateUtils::redstone, getFlag(FLAG_BASIC_EXPLOSIVES));
        registerTNT(ID_SLIME_TNT, DetonateUtils::slime, getFlag(FLAG_BASIC_EXPLOSIVES));

        registerTNT(ID_PHYTO_TNT, DetonateUtils::phyto, getFlag(FLAG_PHYTOGRO_EXPLOSIVES));
        registerTNT(ID_NUKE_TNT, DetonateUtils::nuke, getFlag(FLAG_NUCLEAR_EXPLOSIVES));
        //registerExplosives("gravity", DetonateUtil::gravity);
    }

    public static void setup() {

        SpawnPlacements.register(BASALZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Basalz::canSpawn);
        SpawnPlacements.register(BLITZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blitz::canSpawn);
        SpawnPlacements.register(BLIZZ.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blizz::canSpawn);
    }

    public static final RegistryObject<EntityType<Basalz>> BASALZ = ENTITIES.register(ID_BASALZ, () -> EntityType.Builder.of(Basalz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune().build(ID_BASALZ));
    public static final RegistryObject<EntityType<Blizz>> BLIZZ = ENTITIES.register(ID_BLIZZ, () -> EntityType.Builder.of(Blizz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLIZZ));
    public static final RegistryObject<EntityType<Blitz>> BLITZ = ENTITIES.register(ID_BLITZ, () -> EntityType.Builder.of(Blitz::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLITZ));

    public static final RegistryObject<EntityType<BasalzProjectile>> BASALZ_PROJECTILE = ENTITIES.register(ID_BASALZ_PROJECTILE, () -> EntityType.Builder.<BasalzProjectile>of(cofh.thermal.core.entity.projectile.BasalzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BASALZ_PROJECTILE));
    public static final RegistryObject<EntityType<BlizzProjectile>> BLIZZ_PROJECTILE = ENTITIES.register(ID_BLIZZ_PROJECTILE, () -> EntityType.Builder.<BlizzProjectile>of(cofh.thermal.core.entity.projectile.BlizzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLIZZ_PROJECTILE));
    public static final RegistryObject<EntityType<BlitzProjectile>> BLITZ_PROJECTILE = ENTITIES.register(ID_BLITZ_PROJECTILE, () -> EntityType.Builder.<BlitzProjectile>of(cofh.thermal.core.entity.projectile.BlitzProjectile::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLITZ_PROJECTILE));

}

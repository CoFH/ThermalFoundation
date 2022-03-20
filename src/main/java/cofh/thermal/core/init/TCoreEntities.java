package cofh.thermal.core.init;

import cofh.thermal.core.entity.item.*;
import cofh.thermal.core.entity.monster.BasalzEntity;
import cofh.thermal.core.entity.monster.BlitzEntity;
import cofh.thermal.core.entity.monster.BlizzEntity;
import cofh.thermal.core.entity.projectile.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import static cofh.thermal.core.ThermalCore.ENTITIES;
import static cofh.thermal.core.init.TCoreReferences.*;
import static cofh.thermal.lib.common.ThermalIDs.*;

public class TCoreEntities {

    private TCoreEntities() {

    }

    public static void register() {

        ENTITIES.register(ID_BASALZ, () -> EntityType.Builder.of(BasalzEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).fireImmune().build(ID_BASALZ));
        ENTITIES.register(ID_BLIZZ, () -> EntityType.Builder.of(BlizzEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLIZZ));
        ENTITIES.register(ID_BLITZ, () -> EntityType.Builder.of(BlitzEntity::new, MobCategory.MONSTER).sized(0.6F, 1.8F).build(ID_BLITZ));

        ENTITIES.register(ID_BASALZ_PROJECTILE, () -> EntityType.Builder.<BasalzProjectileEntity>of(BasalzProjectileEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BASALZ_PROJECTILE));
        ENTITIES.register(ID_BLIZZ_PROJECTILE, () -> EntityType.Builder.<BlizzProjectileEntity>of(BlizzProjectileEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLIZZ_PROJECTILE));
        ENTITIES.register(ID_BLITZ_PROJECTILE, () -> EntityType.Builder.<BlitzProjectileEntity>of(BlitzProjectileEntity::new, MobCategory.MISC).sized(0.3125F, 0.3125F).build(ID_BLITZ_PROJECTILE));

        ENTITIES.register(ID_EXPLOSIVE_GRENADE, () -> EntityType.Builder.<ExplosiveGrenadeEntity>of(ExplosiveGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_EXPLOSIVE_GRENADE));

        ENTITIES.register(ID_SLIME_GRENADE, () -> EntityType.Builder.<SlimeGrenadeEntity>of(SlimeGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_SLIME_GRENADE));
        ENTITIES.register(ID_REDSTONE_GRENADE, () -> EntityType.Builder.<RedstoneGrenadeEntity>of(RedstoneGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_REDSTONE_GRENADE));
        ENTITIES.register(ID_GLOWSTONE_GRENADE, () -> EntityType.Builder.<GlowstoneGrenadeEntity>of(GlowstoneGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_GLOWSTONE_GRENADE));
        ENTITIES.register(ID_ENDER_GRENADE, () -> EntityType.Builder.<EnderGrenadeEntity>of(EnderGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_ENDER_GRENADE));

        ENTITIES.register(ID_PHYTO_GRENADE, () -> EntityType.Builder.<PhytoGrenadeEntity>of(PhytoGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_PHYTO_GRENADE));

        ENTITIES.register(ID_FIRE_GRENADE, () -> EntityType.Builder.<FireGrenadeEntity>of(FireGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_FIRE_GRENADE));
        ENTITIES.register(ID_EARTH_GRENADE, () -> EntityType.Builder.<EarthGrenadeEntity>of(EarthGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_EARTH_GRENADE));
        ENTITIES.register(ID_ICE_GRENADE, () -> EntityType.Builder.<IceGrenadeEntity>of(IceGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_ICE_GRENADE));
        ENTITIES.register(ID_LIGHTNING_GRENADE, () -> EntityType.Builder.<LightningGrenadeEntity>of(LightningGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_LIGHTNING_GRENADE));

        ENTITIES.register(ID_NUKE_GRENADE, () -> EntityType.Builder.<NukeGrenadeEntity>of(NukeGrenadeEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).build(ID_NUKE_GRENADE));

        ENTITIES.register(ID_SLIME_TNT, () -> EntityType.Builder.<SlimeTNTEntity>of(SlimeTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_SLIME_TNT));
        ENTITIES.register(ID_REDSTONE_TNT, () -> EntityType.Builder.<RedstoneTNTEntity>of(RedstoneTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_REDSTONE_TNT));
        ENTITIES.register(ID_GLOWSTONE_TNT, () -> EntityType.Builder.<GlowstoneTNTEntity>of(GlowstoneTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_GLOWSTONE_TNT));
        ENTITIES.register(ID_ENDER_TNT, () -> EntityType.Builder.<EnderTNTEntity>of(EnderTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_ENDER_TNT));

        ENTITIES.register(ID_PHYTO_TNT, () -> EntityType.Builder.<PhytoTNTEntity>of(PhytoTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_PHYTO_TNT));

        ENTITIES.register(ID_FIRE_TNT, () -> EntityType.Builder.<FireTNTEntity>of(FireTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_FIRE_TNT));
        ENTITIES.register(ID_EARTH_TNT, () -> EntityType.Builder.<EarthTNTEntity>of(EarthTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_EARTH_TNT));
        ENTITIES.register(ID_ICE_TNT, () -> EntityType.Builder.<IceTNTEntity>of(IceTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_ICE_TNT));
        ENTITIES.register(ID_LIGHTNING_TNT, () -> EntityType.Builder.<LightningTNTEntity>of(LightningTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_LIGHTNING_TNT));

        ENTITIES.register(ID_NUKE_TNT, () -> EntityType.Builder.<NukeTNTEntity>of(NukeTNTEntity::new, MobCategory.MISC).fireImmune().sized(0.98F, 0.98F).build(ID_NUKE_TNT));
    }

    public static void setup() {

        SpawnPlacements.register(BASALZ_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BasalzEntity::canSpawn);
        SpawnPlacements.register(BLITZ_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlitzEntity::canSpawn);
        SpawnPlacements.register(BLIZZ_ENTITY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BlizzEntity::canSpawn);
    }

}

package cofh.thermal.core.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;

import static cofh.lib.util.constants.ModIds.ID_THERMAL;
import static cofh.thermal.core.ThermalCore.SOUND_EVENTS;

public class TCoreSounds {

    private TCoreSounds() {

    }

    public static void register() {

    }

    public static RegistryObject<SoundEvent> registerSound(String soundID) {

        return SOUND_EVENTS.register(soundID, () -> new SoundEvent(new ResourceLocation(ID_THERMAL, soundID)));
    }

    // region IDs
    public static final String ID_SOUND_DEVICE_WATER_GEN = "block.device_water_gen";

    public static final String ID_SOUND_BASALZ_AMBIENT = "entity.basalz.ambient";
    public static final String ID_SOUND_BASALZ_DEATH = "entity.basalz.death";
    public static final String ID_SOUND_BASALZ_HURT = "entity.basalz.hurt";
    public static final String ID_SOUND_BASALZ_ROAM = "entity.basalz.roam";
    public static final String ID_SOUND_BASALZ_SHOOT = "entity.basalz.shoot";

    public static final String ID_SOUND_BLITZ_AMBIENT = "entity.blitz.ambient";
    public static final String ID_SOUND_BLITZ_DEATH = "entity.blitz.death";
    public static final String ID_SOUND_BLITZ_HURT = "entity.blitz.hurt";
    public static final String ID_SOUND_BLITZ_ROAM = "entity.blitz.roam";
    public static final String ID_SOUND_BLITZ_SHOOT = "entity.blitz.shoot";

    public static final String ID_SOUND_BLIZZ_AMBIENT = "entity.blizz.ambient";
    public static final String ID_SOUND_BLIZZ_DEATH = "entity.blizz.death";
    public static final String ID_SOUND_BLIZZ_HURT = "entity.blizz.hurt";
    public static final String ID_SOUND_BLIZZ_ROAM = "entity.blizz.roam";
    public static final String ID_SOUND_BLIZZ_SHOOT = "entity.blizz.shoot";

    public static final String ID_SOUND_MAGNET = "item.magnet";
    public static final String ID_SOUND_TINKER = "misc.tinker";

    public static final String ID_SOUND_ARMOR_BEEKEEPER = "item.armor.equip_beekeeper";
    public static final String ID_SOUND_ARMOR_DIVING = "item.armor.equip_diving";
    public static final String ID_SOUND_ARMOR_HAZMAT = "item.armor.equip_hazmat";
    // endregion

    public static final RegistryObject<SoundEvent> SOUND_DEVICE_WATER_GEN = registerSound(ID_SOUND_DEVICE_WATER_GEN);
    public static final RegistryObject<SoundEvent> SOUND_BASALZ_AMBIENT = registerSound(ID_SOUND_BASALZ_AMBIENT);
    public static final RegistryObject<SoundEvent> SOUND_BASALZ_ROAM = registerSound(ID_SOUND_BASALZ_ROAM);
    public static final RegistryObject<SoundEvent> SOUND_BASALZ_DEATH = registerSound(ID_SOUND_BASALZ_DEATH);
    public static final RegistryObject<SoundEvent> SOUND_BASALZ_HURT = registerSound(ID_SOUND_BASALZ_HURT);
    public static final RegistryObject<SoundEvent> SOUND_BASALZ_SHOOT = registerSound(ID_SOUND_BASALZ_SHOOT);
    public static final RegistryObject<SoundEvent> SOUND_BLITZ_AMBIENT = registerSound(ID_SOUND_BLITZ_AMBIENT);
    public static final RegistryObject<SoundEvent> SOUND_BLITZ_ROAM = registerSound(ID_SOUND_BLITZ_ROAM);
    public static final RegistryObject<SoundEvent> SOUND_BLITZ_DEATH = registerSound(ID_SOUND_BLITZ_DEATH);
    public static final RegistryObject<SoundEvent> SOUND_BLITZ_HURT = registerSound(ID_SOUND_BLITZ_HURT);
    public static final RegistryObject<SoundEvent> SOUND_BLITZ_SHOOT = registerSound(ID_SOUND_BLITZ_SHOOT);
    public static final RegistryObject<SoundEvent> SOUND_BLIZZ_AMBIENT = registerSound(ID_SOUND_BLIZZ_AMBIENT);
    public static final RegistryObject<SoundEvent> SOUND_BLIZZ_ROAM = registerSound(ID_SOUND_BLIZZ_ROAM);
    public static final RegistryObject<SoundEvent> SOUND_BLIZZ_DEATH = registerSound(ID_SOUND_BLIZZ_DEATH);
    public static final RegistryObject<SoundEvent> SOUND_BLIZZ_HURT = registerSound(ID_SOUND_BLIZZ_HURT);
    public static final RegistryObject<SoundEvent> SOUND_BLIZZ_SHOOT = registerSound(ID_SOUND_BLIZZ_SHOOT);
    public static final RegistryObject<SoundEvent> SOUND_MAGNET = registerSound(ID_SOUND_MAGNET);
    public static final RegistryObject<SoundEvent> SOUND_TINKER = registerSound(ID_SOUND_TINKER);
    public static final RegistryObject<SoundEvent> SOUND_ARMOR_BEEKEEPER = registerSound(ID_SOUND_ARMOR_BEEKEEPER);
    public static final RegistryObject<SoundEvent> SOUND_ARMOR_DIVING = registerSound(ID_SOUND_ARMOR_DIVING);
    public static final RegistryObject<SoundEvent> SOUND_ARMOR_HAZMAT = registerSound(ID_SOUND_ARMOR_HAZMAT);

}

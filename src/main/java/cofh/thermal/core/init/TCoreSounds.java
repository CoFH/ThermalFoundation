package cofh.thermal.core.init;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.ObjectHolder;

import static cofh.lib.util.constants.Constants.ID_THERMAL;
import static cofh.thermal.core.util.RegistrationHelper.registerSound;

public class TCoreSounds {

    private TCoreSounds() {

    }

    public static void register() {

        registerSound(ID_SOUND_DEVICE_WATER_GEN);

        registerSound(ID_SOUND_BASALZ_AMBIENT);
        registerSound(ID_SOUND_BASALZ_ROAM);
        registerSound(ID_SOUND_BASALZ_DEATH);
        registerSound(ID_SOUND_BASALZ_HURT);
        registerSound(ID_SOUND_BASALZ_SHOOT);

        registerSound(ID_SOUND_BLITZ_AMBIENT);
        registerSound(ID_SOUND_BLITZ_ROAM);
        registerSound(ID_SOUND_BLITZ_DEATH);
        registerSound(ID_SOUND_BLITZ_HURT);
        registerSound(ID_SOUND_BLITZ_SHOOT);

        registerSound(ID_SOUND_BLIZZ_AMBIENT);
        registerSound(ID_SOUND_BLIZZ_ROAM);
        registerSound(ID_SOUND_BLIZZ_DEATH);
        registerSound(ID_SOUND_BLIZZ_HURT);
        registerSound(ID_SOUND_BLIZZ_SHOOT);

        registerSound(ID_SOUND_MAGNET);
        registerSound(ID_SOUND_TINKER);
    }

    // region IDs
    public static final String ID_SOUND_DEVICE_WATER_GEN = ID_THERMAL + ":block.device_water_gen";

    public static final String ID_SOUND_BASALZ_AMBIENT = ID_THERMAL + ":entity.basalz.ambient";
    public static final String ID_SOUND_BASALZ_DEATH = ID_THERMAL + ":entity.basalz.death";
    public static final String ID_SOUND_BASALZ_HURT = ID_THERMAL + ":entity.basalz.hurt";
    public static final String ID_SOUND_BASALZ_ROAM = ID_THERMAL + ":entity.basalz.roam";
    public static final String ID_SOUND_BASALZ_SHOOT = ID_THERMAL + ":entity.basalz.shoot";

    public static final String ID_SOUND_BLITZ_AMBIENT = ID_THERMAL + ":entity.blitz.ambient";
    public static final String ID_SOUND_BLITZ_DEATH = ID_THERMAL + ":entity.blitz.death";
    public static final String ID_SOUND_BLITZ_HURT = ID_THERMAL + ":entity.blitz.hurt";
    public static final String ID_SOUND_BLITZ_ROAM = ID_THERMAL + ":entity.blitz.roam";
    public static final String ID_SOUND_BLITZ_SHOOT = ID_THERMAL + ":entity.blitz.shoot";

    public static final String ID_SOUND_BLIZZ_AMBIENT = ID_THERMAL + ":entity.blizz.ambient";
    public static final String ID_SOUND_BLIZZ_DEATH = ID_THERMAL + ":entity.blizz.death";
    public static final String ID_SOUND_BLIZZ_HURT = ID_THERMAL + ":entity.blizz.hurt";
    public static final String ID_SOUND_BLIZZ_ROAM = ID_THERMAL + ":entity.blizz.roam";
    public static final String ID_SOUND_BLIZZ_SHOOT = ID_THERMAL + ":entity.blizz.shoot";

    public static final String ID_SOUND_MAGNET = ID_THERMAL + ":item.magnet";
    public static final String ID_SOUND_TINKER = ID_THERMAL + ":misc.tinker";
    // endregion

    // region REFERENCES
    @ObjectHolder(ID_SOUND_DEVICE_WATER_GEN)
    public static final SoundEvent SOUND_DEVICE_WATER_GEN = null;

    @ObjectHolder(ID_SOUND_BASALZ_AMBIENT)
    public static final SoundEvent SOUND_BASALZ_AMBIENT = null;
    @ObjectHolder(ID_SOUND_BASALZ_DEATH)
    public static final SoundEvent SOUND_BASALZ_DEATH = null;
    @ObjectHolder(ID_SOUND_BASALZ_HURT)
    public static final SoundEvent SOUND_BASALZ_HURT = null;
    @ObjectHolder(ID_SOUND_BASALZ_ROAM)
    public static final SoundEvent SOUND_BASALZ_ROAM = null;
    @ObjectHolder(ID_SOUND_BASALZ_SHOOT)
    public static final SoundEvent SOUND_BASALZ_SHOOT = null;

    @ObjectHolder(ID_SOUND_BLITZ_AMBIENT)
    public static final SoundEvent SOUND_BLITZ_AMBIENT = null;
    @ObjectHolder(ID_SOUND_BLITZ_DEATH)
    public static final SoundEvent SOUND_BLITZ_DEATH = null;
    @ObjectHolder(ID_SOUND_BLITZ_HURT)
    public static final SoundEvent SOUND_BLITZ_HURT = null;
    @ObjectHolder(ID_SOUND_BLITZ_ROAM)
    public static final SoundEvent SOUND_BLITZ_ROAM = null;
    @ObjectHolder(ID_SOUND_BLITZ_SHOOT)
    public static final SoundEvent SOUND_BLITZ_SHOOT = null;

    @ObjectHolder(ID_SOUND_BLIZZ_AMBIENT)
    public static final SoundEvent SOUND_BLIZZ_AMBIENT = null;
    @ObjectHolder(ID_SOUND_BLIZZ_DEATH)
    public static final SoundEvent SOUND_BLIZZ_DEATH = null;
    @ObjectHolder(ID_SOUND_BLIZZ_HURT)
    public static final SoundEvent SOUND_BLIZZ_HURT = null;
    @ObjectHolder(ID_SOUND_BLIZZ_ROAM)
    public static final SoundEvent SOUND_BLIZZ_ROAM = null;
    @ObjectHolder(ID_SOUND_BLIZZ_SHOOT)
    public static final SoundEvent SOUND_BLIZZ_SHOOT = null;

    @ObjectHolder(ID_SOUND_MAGNET)
    public static final SoundEvent SOUND_MAGNET = null;

    @ObjectHolder(ID_SOUND_TINKER)
    public static final SoundEvent SOUND_TINKER = null;
    // endregion
}

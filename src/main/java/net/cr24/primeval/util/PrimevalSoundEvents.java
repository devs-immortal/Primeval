package net.cr24.primeval.util;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class PrimevalSoundEvents {

    public static final SoundEvent QUERN_BREAK = registerSound("quern_break");
    public static final SoundEvent QUERN_GRIND = registerSound("quern_grind");
    public static final SoundEvent QUERN_PROCESS = registerSound("quern_process");

    public static void init() {}
    
    private static SoundEvent registerSound(String id) {
        Identifier located = PrimevalMain.getId(id);
        return Registry.register(Registries.SOUND_EVENT, located, SoundEvent.of(located));
    }
}

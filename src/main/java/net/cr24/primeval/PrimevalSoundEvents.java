package net.cr24.primeval;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class PrimevalSoundEvents {

    public static final SoundEvent QUERN_BREAK = registerSound("quern_break");
    public static final SoundEvent QUERN_GRIND = registerSound("quern_grind");
    public static final SoundEvent QUERN_PROCESS = registerSound("quern_process");

    public static void init() {}

    private static SoundEvent registerSound(String id) {
        Identifier located = Primeval.identify(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, located, SoundEvent.createVariableRangeEvent(located));
    }
}

package net.cr24.primeval.util;

import net.minecraft.network.chat.Component;

public enum Weight {
    VERY_LIGHT,
    LIGHT,
    NORMAL,
    HEAVY,
    VERY_HEAVY;

    public Component getText() {
        switch(this) {
            case VERY_LIGHT:
                return Component.translatable("text.primeval.weight.very_light");
            case LIGHT:
                return Component.translatable("text.primeval.weight.light");
            case NORMAL:
                return Component.translatable("text.primeval.weight.normal");
            case HEAVY:
                return Component.translatable("text.primeval.weight.heavy");
            case VERY_HEAVY:
                return Component.translatable("text.primeval.weight.very_heavy");
        }
        return Component.translatable("text.primeval.weight.normal");
    }

}

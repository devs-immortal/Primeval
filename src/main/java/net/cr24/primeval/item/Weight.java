package net.cr24.primeval.item;

import net.minecraft.text.TranslatableText;

public enum Weight {
    VERY_LIGHT,
    LIGHT,
    NORMAL,
    HEAVY,
    VERY_HEAVY;

    public TranslatableText getText() {
        switch(this) {
            case VERY_LIGHT:
                return new TranslatableText("text.primeval.weight.very_light");
            case LIGHT:
                return new TranslatableText("text.primeval.weight.light");
            case NORMAL:
                return new TranslatableText("text.primeval.weight.normal");
            case HEAVY:
                return new TranslatableText("text.primeval.weight.heavy");
            case VERY_HEAVY:
                return new TranslatableText("text.primeval.weight.very_heavy");
        }
        return new TranslatableText("text.primeval.weight.normal");
    }

}

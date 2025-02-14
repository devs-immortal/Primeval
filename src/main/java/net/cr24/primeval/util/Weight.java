package net.cr24.primeval.util;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public enum Weight {
    VERY_LIGHT,
    LIGHT,
    NORMAL,
    HEAVY,
    VERY_HEAVY;

    public Text getText() {
        switch(this) {
            case VERY_LIGHT:
                return Text.translatable("text.primeval.weight.very_light");
            case LIGHT:
                return Text.translatable("text.primeval.weight.light");
            case NORMAL:
                return Text.translatable("text.primeval.weight.normal");
            case HEAVY:
                return Text.translatable("text.primeval.weight.heavy");
            case VERY_HEAVY:
                return Text.translatable("text.primeval.weight.very_heavy");
        }
        return Text.translatable("text.primeval.weight.normal");
    }

}

package net.cr24.primeval.item;

import net.minecraft.text.TranslatableText;

public enum Size {
    SMALL,      // normal 64 stack
    MEDIUM,     // max 32 in stack
    LARGE,      // max 16 in stack
    VERY_LARGE;  // max 1 in stack

    public TranslatableText getText() {
        switch(this) {
            case SMALL:
                return new TranslatableText("text.primeval.size.small");
            case MEDIUM:
                return new TranslatableText("text.primeval.size.medium");
            case LARGE:
                return new TranslatableText("text.primeval.size.large");
            case VERY_LARGE:
                return new TranslatableText("text.primeval.size.very_large");
        }
        return new TranslatableText("text.primeval.size.medium");
    }

    public int getStackSize() {
        switch(this) {
            case SMALL:
                return 64;
            case MEDIUM:
                return 32;
            case LARGE:
                return 16;
            case VERY_LARGE:
                return 1;
        }
        return 64;
    }

}

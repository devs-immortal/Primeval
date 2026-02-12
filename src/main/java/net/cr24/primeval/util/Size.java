package net.cr24.primeval.util;

import net.minecraft.network.chat.Component;

public enum Size {
    SMALL,      // normal 64 stack
    MEDIUM,     // max 32 in stack
    LARGE,      // max 16 in stack
    VERY_LARGE;  // max 1 in stack

    public Component getText() {
        switch(this) {
            case SMALL:
                return Component.translatable("text.primeval.size.small");
            case MEDIUM:
                return Component.translatable("text.primeval.size.medium");
            case LARGE:
                return Component.translatable("text.primeval.size.large");
            case VERY_LARGE:
                return Component.translatable("text.primeval.size.very_large");
        }
        return Component.translatable("text.primeval.size.medium");
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

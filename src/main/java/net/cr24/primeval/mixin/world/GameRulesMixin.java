package net.cr24.primeval.mixin.world;

import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @Shadow
    @Final
    @Mutable
    private static Map<GameRules.Key<?>, GameRules.Type<?>> RULE_TYPES;

    @Shadow
    @Final
    @Mutable
    public static final GameRules.Key<GameRules.BooleanRule> DO_INSOMNIA = jankRegister("doInsomnia", GameRules.Category.SPAWNING, GameRules.BooleanRule.create(false));

    private static <T extends GameRules.Rule<T>> GameRules.Key<T> jankRegister(String name, GameRules.Category category, GameRules.Type<T> type) {
        GameRules.Key key = new GameRules.Key(name, category);
        GameRules.Type<T> type2 = (GameRules.Type<T>) RULE_TYPES.put(key, type);
        return key;
    }

}

package net.cr24.primeval.mixin.world;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @Inject(method = "registerBoolean", at = @At("HEAD"), cancellable = true)
    private static void registerBooleanRule(String name, GameRuleCategory category, boolean defaultValue, CallbackInfoReturnable<GameRule<Boolean>> cir) {
        if (name.equals("spawn_phantoms") || name.equals("spawn_patrols")) {
            cir.setReturnValue(GameRules.register(name, category, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, false, FeatureFlagSet.of(), GameRuleTypeVisitor::visitBoolean, (value) -> value ? 1 : 0));
        }
    }

}

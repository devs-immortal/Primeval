package net.cr24.primeval.mixin.world;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.serialization.Codec;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.world.rule.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRules.class)
public class GameRulesMixin {

    @Inject(method = "registerBooleanRule", at = @At("HEAD"), cancellable = true)
    private static void registerBooleanRule(String name, GameRuleCategory category, boolean defaultValue, CallbackInfoReturnable<GameRule<Boolean>> cir) {
        if (name.equals("spawn_phantoms") || name.equals("spawn_patrols")) {
            cir.setReturnValue(GameRules.register(name, category, GameRuleType.BOOL, BoolArgumentType.bool(), Codec.BOOL, false, FeatureSet.empty(), GameRuleVisitor::visitBoolean, (value) -> value ? 1 : 0));
        }
    }

}

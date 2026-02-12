package net.cr24.primeval.initialization;

import java.util.Arrays;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public class PrimevalItemActions {

    public static FoodProperties foodComponent(int nutrition, float saturationModifier) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build();
    }

    public static FoodProperties foodComponent(int nutrition, float saturationModifier, boolean alwaysEat) {
        if (alwaysEat) {
            return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).alwaysEdible().build();
        } else {
            return new FoodProperties.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build();
        }
    }

    public static net.minecraft.world.item.component.Consumable consumableComponent(Consumable... consumeEffects) {
        return new net.minecraft.world.item.component.Consumable(1.6f, ItemUseAnimation.EAT, SoundEvents.GENERIC_EAT, true, Arrays.stream(consumeEffects).map(Consumable::asConsumeEffect).toList());
    }

    public record Consumable(Holder<MobEffect> effect, int duration, int amplifier, float chance) {
        public ConsumeEffect asConsumeEffect() {
            return new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(effect, duration, amplifier), chance);
        }
    }

}

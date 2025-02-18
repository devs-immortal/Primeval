package net.cr24.primeval.initialization;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.ConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;

import java.util.Arrays;
import java.util.List;

public class PrimevalItemActions {

    public static FoodComponent foodComponent(int nutrition, float saturationModifier) {
        return new FoodComponent.Builder().nutrition(nutrition).saturationModifier(saturationModifier).build();
    }

    public static ConsumableComponent consumableComponent(Consumable... consumeEffects) {
        return new ConsumableComponent(1.6f, UseAction.EAT, SoundEvents.ENTITY_GENERIC_EAT, true, Arrays.stream(consumeEffects).map(Consumable::asConsumeEffect).toList());
    }

    public record Consumable(RegistryEntry<StatusEffect> effect, int duration, int amplifier, float chance) {
        public ConsumeEffect asConsumeEffect() {
            return new ApplyEffectsConsumeEffect(new StatusEffectInstance(effect, duration, amplifier), chance);
        }
    }

}

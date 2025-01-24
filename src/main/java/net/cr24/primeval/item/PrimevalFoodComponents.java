package net.cr24.primeval.item;

import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;
import net.minecraft.item.consume.UseAction;
import net.minecraft.sound.SoundEvents;

public class PrimevalFoodComponents {

    public static final FoodComponent PORKCHOP_FOOD = createFoodComponent(2, 0.3f);
    public static final ConsumableComponent PORKCHOP_CONSUME = createConsumableComponent()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3f)).build();

    public static final FoodComponent COOKED_PORKCHOP_FOOD = createFoodComponent(8, 0.8f);

    public static final FoodComponent CARROT_FOOD = createFoodComponent(4, 1f);

    public static final FoodComponent CABBAGE_FOOD = createFoodComponent(2, 4f);

    public static final FoodComponent BEANS_FOOD = createFoodComponent(1, 2f);

    public static final FoodComponent POTATO_FOOD = createFoodComponent(3, 0f);

    public static final FoodComponent ROTTEN_FLESH_FOOD = createFoodComponent(4, 0.1f);
    public static final ConsumableComponent ROTTEN_FLESH_CONSUME = createConsumableComponent()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 0), 0.8f))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 0.8f)).build();

    public static final FoodComponent SPIDER_EYE_FOOD = createFoodComponent(4, 0.1f);
    public static final ConsumableComponent SPIDER_EYE_CONSUME = createConsumableComponent()
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 0), 0.4f))
            .consumeEffect(new ApplyEffectsConsumeEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 1), 0.9f)).build();


    public static void init() {}

    private static FoodComponent createFoodComponent(int hunger, float saturation) {
        return (new FoodComponent.Builder()).nutrition(hunger).saturationModifier(saturation).build();
    }

    public static ConsumableComponent.Builder createConsumableComponent() {
        return ConsumableComponent.builder().consumeSeconds(1.6F).useAction(UseAction.EAT).sound(SoundEvents.ENTITY_GENERIC_EAT).consumeParticles(true);
    }

}

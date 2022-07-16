package net.cr24.primeval.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class PrimevalFoodComponents {

    public static final FoodComponent PORKCHOP = createFoodComponent(2, 0.3f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 600, 0), 0.3F)
            .meat().build();
    public static final FoodComponent COOKED_PORKCHOP = createFoodComponent(8, 0.8f)
            .meat().build();
    public static final FoodComponent CARROT = createFoodComponent(4, 1f)
            .build();
    public static final FoodComponent ROTTEN_FLESH = createFoodComponent(4, 0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 300, 1), 0.8F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), 0.8F)
            .meat().build();
    public static final FoodComponent SPIDER_EYE = createFoodComponent(4, 0.1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 0), 0.4F)
            .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 300, 1), 0.8F)
            .meat().build();


    public static void init() {}

    private static FoodComponent.Builder createFoodComponent(int hunger, float saturation) {
        return (new FoodComponent.Builder()).hunger(hunger).saturationModifier(saturation);
    }
}

package net.cr24.primeval.mixin.entity.passive;

import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(PigEntity.class)
public class PigEntityMixin {

    @Shadow
    @Final
    @Mutable
    private static Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{PrimevalItems.CARROT, PrimevalItems.POTATO});

}

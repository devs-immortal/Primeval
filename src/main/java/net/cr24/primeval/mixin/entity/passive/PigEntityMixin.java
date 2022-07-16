package net.cr24.primeval.mixin.entity.passive;

import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public class PigEntityMixin {

    @Inject(method = "isBreedingItem", at = @At("HEAD"), cancellable = true)
    private void isBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> info) {
        info.setReturnValue(stack.isOf(PrimevalItems.CARROT));
    }

}

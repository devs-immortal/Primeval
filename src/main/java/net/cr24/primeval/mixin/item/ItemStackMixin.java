package net.cr24.primeval.mixin.item;

import net.cr24.primeval.item.PrimevalItemTags;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onItemEntityDestroyed", at = @At(value = "HEAD"))
    public void onItemEntityDestroyed(ItemEntity entity, CallbackInfo info) {
        ItemStack stack = entity.getStack();
        if (stack.isIn(PrimevalItemTags.BURNS_TO_ASH)) entity.dropStack(new ItemStack(PrimevalItems.ASHES, stack.getCount()));
    }
}

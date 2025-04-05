package net.cr24.primeval.mixin.item;

import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onItemEntityDestroyed", at = @At(value = "HEAD"))
    public void onItemEntityDestroyed(ItemEntity entity, CallbackInfo info) {
        ItemStack stack = entity.getStack();
        if (stack.isIn(PrimevalTags.Items.BURNS_TO_ASH)) ItemUsage.spawnItemContents(entity, List.of(new ItemStack(PrimevalItems.ASHES, stack.getCount())));
    }
}

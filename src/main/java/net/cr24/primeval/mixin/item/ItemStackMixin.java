package net.cr24.primeval.mixin.item;

import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "onDestroyed", at = @At(value = "HEAD"))
    public void onItemEntityDestroyed(ItemEntity entity, CallbackInfo info) {
        ItemStack stack = entity.getItem();
        if (stack.is(PrimevalTags.Items.BURNS_TO_ASH)) ItemUtils.onContainerDestroyed(entity, List.of(new ItemStack(PrimevalItems.ASHES, stack.getCount())));
    }
}

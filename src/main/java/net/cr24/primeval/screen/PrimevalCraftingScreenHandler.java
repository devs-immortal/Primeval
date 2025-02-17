package net.cr24.primeval.screen;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

public class PrimevalCraftingScreenHandler extends CraftingScreenHandler {

    private final ScreenHandlerContext context;

    public PrimevalCraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(syncId, playerInventory, context);
        this.context = context;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return CraftingScreenHandler.canUse(this.context, player, PrimevalBlocks.CRUDE_CRAFTING_BENCH);
    }
}

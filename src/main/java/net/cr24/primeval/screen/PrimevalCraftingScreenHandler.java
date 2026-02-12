package net.cr24.primeval.screen;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;

public class PrimevalCraftingScreenHandler extends CraftingMenu {

    private final ContainerLevelAccess context;

    public PrimevalCraftingScreenHandler(int syncId, Inventory playerInventory, ContainerLevelAccess context) {
        super(syncId, playerInventory, context);
        this.context = context;
    }

    @Override
    public boolean stillValid(Player player) {
        return CraftingMenu.stillValid(this.context, player, PrimevalBlocks.CRUDE_CRAFTING_BENCH);
    }
}

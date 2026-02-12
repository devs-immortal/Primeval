package net.cr24.primeval.screen;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class Primeval3x5ContainerScreenHandler extends AbstractContainerMenu {

    private final Container inventory;

    public static Primeval3x5ContainerScreenHandler create(int syncId, Inventory playerInventory) {
        return new Primeval3x5ContainerScreenHandler(syncId, playerInventory, new SimpleContainer(15));
    }

    public static Primeval3x5ContainerScreenHandler create(int syncId, Inventory playerInventory, Container inventory) {
        return new Primeval3x5ContainerScreenHandler(syncId, playerInventory, inventory);
    }

    public Primeval3x5ContainerScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(PrimevalScreens.GENERIC_3X5_HANDLER, syncId);
        checkContainerSize(inventory, 15);
        this.inventory = inventory;
        inventory.startOpen(playerInventory.player);

        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 5; ++l) {
                this.addSlot(new Slot(inventory, l + m * 5, 44 + l * 18, 17 + m * 18));
            }
        }
        //player inventory
        for (int m = 0; m < 3; ++m) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //player hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (index < this.inventory.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.inventory.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.inventory.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return newStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public Container getInventory() {
        return this.inventory;
    }
}

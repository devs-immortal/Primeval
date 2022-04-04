package net.cr24.primeval.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class FluidInventory implements Inventory {

    private final HashMap<FluidVariant, Integer> fluids;

    public FluidInventory(HashMap<FluidVariant, Integer> fluidsIn) {
        this.fluids = fluidsIn;
    }

    @Override
    public int size() {
        return fluids.size();
    }

    @Override
    public boolean isEmpty() {
        return fluids.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return ItemStack.EMPTY;
    }

    public HashMap<FluidVariant, Integer> getFluids() {
        return this.fluids;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {

    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        this.fluids.clear();
    }
}

package net.cr24.primeval.util;

import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class FluidInput implements RecipeInput {

    Map<RegistryEntry<Fluid>, Integer> fluidContents;

    public FluidInput(Map<RegistryEntry<Fluid>, Integer> fluids) {
        this.fluidContents = fluids;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return fluidContents.isEmpty();
    }

    public Map<RegistryEntry<Fluid>, Integer> getContents() {
        return fluidContents;
    }
}

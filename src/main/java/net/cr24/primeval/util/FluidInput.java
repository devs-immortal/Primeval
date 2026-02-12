package net.cr24.primeval.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.material.Fluid;

public class FluidInput implements RecipeInput {

    Map<Holder<Fluid>, Integer> fluidContents;

    public FluidInput(Map<Holder<Fluid>, Integer> fluids) {
        this.fluidContents = fluids;
    }

    @Override
    public ItemStack getItem(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }

    public boolean isEmpty() {
        return fluidContents.isEmpty();
    }

    public Map<Holder<Fluid>, Integer> getContents() {
        return fluidContents;
    }
}

package net.cr24.primeval.recipe;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.stream.Stream;

public abstract class SimpleOneToOneRecipe implements Recipe<Inventory> {
    final Ingredient input;
    final ItemStack result;
    private final Identifier id;

    public SimpleOneToOneRecipe(Identifier id, Ingredient input, ItemStack result) {
            this.id = id;
            this.input = input;
            this.result = result;
    }

    public boolean matches(Inventory inventory, World world) {
            return this.input.test(inventory.getStack(0));
    }

    public ItemStack craft(Inventory inventory) {
            return this.result.copy();
    }

    public boolean fits(int width, int height) {
            return width * height >= 2;
    }

    public ItemStack getOutput() {
            return this.result;
    }

    public abstract ItemStack createIcon();

    public Identifier getId() {
            return this.id;
    }

    public abstract RecipeSerializer<?> getSerializer();

    public abstract RecipeType<?> getType();

    public boolean isEmpty() {
            return Stream.of(this.input).anyMatch((ingredient) -> ingredient.getMatchingStacks().length == 0);
    }
}
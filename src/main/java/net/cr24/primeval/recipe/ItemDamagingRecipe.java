package net.cr24.primeval.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ItemDamagingRecipe implements CraftingRecipe {
    private final Identifier id;
    private final String group;
    private final ItemStack output;
    private final DefaultedList<Ingredient> input;

    public ItemDamagingRecipe(Identifier id, String group, ItemStack output, DefaultedList<Ingredient> input) {
        this.id = id;
        this.group = group;
        this.output = output;
        this.input = input;
    }

    @Override
    public boolean matches(CraftingInventory craftingInventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int i = 0;
        for (int j = 0; j < craftingInventory.size(); ++j) {
            ItemStack itemStack = craftingInventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
            recipeMatcher.addInput(itemStack, 1);
        }
        return i == this.input.size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(CraftingInventory craftingInventory) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.input.size();
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.input;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(CraftingInventory inventory) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < defaultedList.size(); ++i) {
            ItemStack item = inventory.getStack(i);
            if (item.getItem().isDamageable()) { // Override damageable, fallback onto remainders
                if (item.getDamage() + 1 < item.getMaxDamage()) {
                    item = item.copy();
                    item.setDamage(item.getDamage() + 1);
                    defaultedList.set(i, item);
                }
            } else if (item.getItem().hasRecipeRemainder()) {
                defaultedList.set(i, new ItemStack(item.getItem().getRecipeRemainder()));
            }
        }
        return defaultedList;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.ITEM_DAMAGING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return CraftingRecipe.super.getType();
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<ItemDamagingRecipe> {
        public Serializer() {}

        public ItemDamagingRecipe read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            DefaultedList<Ingredient> defaultedList = ItemDamagingRecipe.Serializer.getIngredients(JsonHelper.getArray(jsonObject, "ingredients"));
            ItemStack itemStack = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new ItemDamagingRecipe(identifier, string, itemStack, defaultedList);
        }

        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();
            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (ingredient.isEmpty()) continue;
                defaultedList.add(ingredient);
            }
            return defaultedList;
        }

        public ItemDamagingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            int i = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> defaultedList = DefaultedList.ofSize(i, Ingredient.EMPTY);
            for (int j = 0; j < defaultedList.size(); ++j) {
                defaultedList.set(j, Ingredient.fromPacket(packetByteBuf));
            }
            ItemStack j = packetByteBuf.readItemStack();
            return new ItemDamagingRecipe(identifier, string, j, defaultedList);
        }

        public void write(PacketByteBuf packetByteBuf, ItemDamagingRecipe shapelessRecipe) {
            packetByteBuf.writeString(shapelessRecipe.group);
            packetByteBuf.writeVarInt(shapelessRecipe.input.size());
            for (Ingredient ingredient : shapelessRecipe.input) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(shapelessRecipe.output);
        }
    }
}

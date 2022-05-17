package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class OpenFireRecipe implements Recipe<Inventory>  {
    final Ingredient input;
    final ItemStack result;
    final int cookTime;
    private final Identifier id;

    public OpenFireRecipe(Identifier id, Ingredient input, ItemStack result, int cookTime) {
        this.id = id;
        this.input = input;
        this.result = result;
        this.cookTime = cookTime;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Blocks.CAMPFIRE);
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.OPEN_FIRE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PrimevalRecipes.OPEN_FIRE;
    }

    public static class Serializer implements RecipeSerializer<OpenFireRecipe> {
        public Serializer() {}

        public OpenFireRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient in = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack out = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            int time = JsonHelper.getInt(jsonObject, "cook_time");
            return new OpenFireRecipe(identifier, in, out, time);
        }

        public OpenFireRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient in = Ingredient.fromPacket(packetByteBuf);
            ItemStack out = packetByteBuf.readItemStack();
            int time = packetByteBuf.readInt();
            return new OpenFireRecipe(identifier, in, out, time);
        }

        public void write(PacketByteBuf packetByteBuf, OpenFireRecipe recipe) {
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.result);
            packetByteBuf.writeInt(recipe.cookTime);
        }
    }
}

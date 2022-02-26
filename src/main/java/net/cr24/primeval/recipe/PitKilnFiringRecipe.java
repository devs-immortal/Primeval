package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class PitKilnFiringRecipe extends SimpleOneToOneRecipe {
    public PitKilnFiringRecipe(Identifier id, Ingredient input, ItemStack result) {
        super(id, input, result);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(PrimevalItems.STRAW);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.PIT_KILN_FIRING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PrimevalRecipes.PIT_KILN_FIRING;
    }

    public static class Serializer implements RecipeSerializer<PitKilnFiringRecipe> {
        public Serializer() {
        }

        public PitKilnFiringRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient in = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack out = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new PitKilnFiringRecipe(identifier, in, out);
        }

        public PitKilnFiringRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient in = Ingredient.fromPacket(packetByteBuf);
            ItemStack out = packetByteBuf.readItemStack();
            return new PitKilnFiringRecipe(identifier, in, out);
        }

        public void write(PacketByteBuf packetByteBuf, PitKilnFiringRecipe recipe) {
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.result);
        }
    }
}
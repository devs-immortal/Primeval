package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

public class OpenFireRecipe extends SimpleOneToOneRecipe {

    final int cookTime;

    public OpenFireRecipe(Ingredient input, ItemStack result, int cookTime) {
        super(input, result);
        this.cookTime = cookTime;
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
    public RecipeSerializer<OpenFireRecipe> getSerializer() {
        return PrimevalRecipes.OPEN_FIRE_SERIALIZER;
    }

    @Override
    public RecipeType<OpenFireRecipe> getType() {
        return PrimevalRecipes.OPEN_FIRE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<OpenFireRecipe> {
        private static final MapCodec<OpenFireRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                Codecs.POSITIVE_INT.fieldOf("cook_time").forGetter((recipe) -> recipe.cookTime)
        ).apply(instance, OpenFireRecipe::new));
        private static final PacketCodec<RegistryByteBuf, OpenFireRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, OpenFireRecipe::getInput,
                ItemStack.PACKET_CODEC, OpenFireRecipe::getResult,
                PacketCodecs.INTEGER, OpenFireRecipe::getCookTime,
                OpenFireRecipe::new
        );

        protected Serializer() {
        }

        public MapCodec<OpenFireRecipe> codec() {
            return this.CODEC;
        }

        public PacketCodec<RegistryByteBuf, OpenFireRecipe> packetCodec() {
            return this.PACKET_CODEC;
        }
    }
}

package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;

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
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<OpenFireRecipe> {
        private static final MapCodec<OpenFireRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                ExtraCodecs.POSITIVE_INT.fieldOf("cook_time").forGetter((recipe) -> recipe.cookTime)
        ).apply(instance, OpenFireRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, OpenFireRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, OpenFireRecipe::getInput,
                ItemStack.STREAM_CODEC, OpenFireRecipe::getResult,
                ByteBufCodecs.INT, OpenFireRecipe::getCookTime,
                OpenFireRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<OpenFireRecipe> codec() {
            return this.CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, OpenFireRecipe> streamCodec() {
            return this.PACKET_CODEC;
        }
    }
}

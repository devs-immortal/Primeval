package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;


public abstract class SimpleOneToOneRecipe implements Recipe<SingleRecipeInput> {

    protected final Ingredient input;
    protected final ItemStack result;

    public SimpleOneToOneRecipe(Ingredient input, ItemStack result) {
            this.input = input;
            this.result = result;
    }

    public boolean matches(SingleRecipeInput input, Level world) {
            return this.input.test(input.item());
    }

    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
            return this.result.copy();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    
    public Ingredient getInput() {return this.input;}

    public ItemStack getResult() {
        return this.result.copy();
    }

    
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public abstract ItemStack createIcon();


    public abstract RecipeSerializer<? extends SimpleOneToOneRecipe> getSerializer();

    public abstract RecipeType<? extends SimpleOneToOneRecipe> getType();


    @FunctionalInterface
    public interface RecipeFactory<T extends SimpleOneToOneRecipe> {
        T create(Ingredient ingredient, ItemStack result);
    }

    public static class Serializer<T extends SimpleOneToOneRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final StreamCodec<RegistryFriendlyByteBuf, T> packetCodec;

        public Serializer(RecipeFactory<T> factory) {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                    ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
            ).apply(instance, factory::create));
            packetCodec = StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, SimpleOneToOneRecipe::getInput,
                    ItemStack.STREAM_CODEC, SimpleOneToOneRecipe::getResult,
                    factory::create
            );
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
            return this.packetCodec;
        }
    }

}
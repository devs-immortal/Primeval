package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class MeltingRecipe implements Recipe<SingleRecipeInput> {

    final Ingredient input;
    final Holder<Fluid> fluidResult;
    final int fluidAmount;

    public MeltingRecipe(Ingredient input, Holder<Fluid> fluidResult, int fluidAmount) {
        this.input = input;
        this.fluidResult = fluidResult;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        return this.input.test(inventory.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public Holder<Fluid> getFluidResult() {
        return this.fluidResult;
    }
    public int getFluidAmount() {
        return this.fluidAmount;
    }

    @Override
    public RecipeSerializer<MeltingRecipe> getSerializer() {
        return PrimevalRecipes.MELTING_SERIALIZER;
    }

    @Override
    public RecipeType<MeltingRecipe> getType() {
        return PrimevalRecipes.MELTING;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<MeltingRecipe> {
        private static final MapCodec<MeltingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                BuiltInRegistries.FLUID.holderByNameCodec().fieldOf("fluid").forGetter((recipe) -> recipe.fluidResult),
                ExtraCodecs.POSITIVE_INT.fieldOf("fluid_amount").forGetter((recipe) -> recipe.fluidAmount)
        ).apply(instance, MeltingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, MeltingRecipe::getInput,
                ByteBufCodecs.holderRegistry(Registries.FLUID), MeltingRecipe::getFluidResult,
                ByteBufCodecs.INT, MeltingRecipe::getFluidAmount,
                MeltingRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<MeltingRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, MeltingRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}

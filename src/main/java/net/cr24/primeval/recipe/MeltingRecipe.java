package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class MeltingRecipe implements Recipe<SingleStackRecipeInput> {

    final Ingredient input;
    final FluidVariant fluidResult;
    final int fluidAmount;

    public MeltingRecipe(Ingredient input, FluidVariant fluidResult, int fluidAmount) {
        this.input = input;
        this.fluidResult = fluidResult;
        this.fluidAmount = fluidAmount;
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return this.input.test(inventory.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public FluidVariant getFluidResult() {
        return this.fluidResult;
    }
    public int getFluidAmount() {
        return this.fluidAmount;
    }

    public Pair<FluidVariant, Integer> getFluidResultPair() {
        return new Pair<>(this.fluidResult, fluidAmount);
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
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<MeltingRecipe> {
        private static final MapCodec<MeltingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                VariantCodecs.FLUID_CODEC.fieldOf("fluid").forGetter((recipe) -> recipe.fluidResult),
                Codecs.POSITIVE_INT.fieldOf("fluid_amount").forGetter((recipe) -> recipe.fluidAmount)
        ).apply(instance, MeltingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, MeltingRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, MeltingRecipe::getInput,
                VariantCodecs.FLUID_PACKET_CODEC, MeltingRecipe::getFluidResult,
                PacketCodecs.INTEGER, MeltingRecipe::getFluidAmount,
                MeltingRecipe::new
        );

        protected Serializer() {
        }

        public MapCodec<MeltingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, MeltingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

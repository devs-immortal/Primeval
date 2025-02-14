package net.cr24.primeval.recipe;

import com.mojang.datafixers.Products;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategories;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Function;

public class PitKilnFiringRecipe extends SimpleOneToOneRecipe {
    public PitKilnFiringRecipe(Ingredient input, ItemStack result) {
        super(input, result);
    }

    @Override
    public RecipeSerializer<PitKilnFiringRecipe> getSerializer() {
        return PrimevalRecipes.PIT_KILN_FIRING_SERIALIZER;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(PrimevalItems.STRAW);
    }

    @Override
    public RecipeType<PitKilnFiringRecipe> getType() {
        return PrimevalRecipes.PIT_KILN_FIRING;
    }

}
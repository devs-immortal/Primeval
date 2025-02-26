package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.fluid.FallbackFluid;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

public class MoldCastingRecipe implements CraftingRecipe {

    final Ingredient mold;
    final RegistryEntry<Fluid> fluid;
    final ItemStack result;

    public MoldCastingRecipe(Ingredient mold, RegistryEntry<Fluid> fluid, ItemStack result) {
        this.result = result;
        this.fluid = fluid;
        this.mold = mold;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.getStackCount() != 1) return false;
        ItemStack stack = input.getStacks().get(0);
        return mold.test(stack) &&
                stack.contains(PrimevalDataComponentTypes.FLUID_CONTENTS) &&
                stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS).fluid().matchesKey(this.fluid.getKey().get());
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        if (input.getStackCount() != 1) return ItemStack.EMPTY;
        ItemStack stack = input.getStacks().get(0);
        var fluidContent = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
        if (fluidContent.amount() < ((MoldItem)stack.getItem()).getCapacity()) {
            Fluid containedFluid = fluidContent.fluid().value();
            if (containedFluid instanceof FallbackFluid fallback) {
                return new ItemStack(fallback.getFallbackItem(), fluidContent.amount() / 1000);
            }
        }
        return this.result.copy();
    }

    public Ingredient getMold() {
        return this.mold;
    }

    public RegistryEntry<Fluid> getFluid() {
        return this.fluid;
    }

    public ItemStack getResult() {
        return this.result;
    }

    @Override
    public RecipeSerializer<? extends CraftingRecipe> getSerializer() {
        return PrimevalRecipes.MOLD_CASTING_SERIALIZER;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(this.mold);
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<MoldCastingRecipe> {
        private static final MapCodec<MoldCastingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("mold").forGetter((recipe) -> recipe.mold),
                Registries.FLUID.getEntryCodec().fieldOf("fluid").forGetter((recipe) -> recipe.fluid),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
        ).apply(instance, MoldCastingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, MoldCastingRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, MoldCastingRecipe::getMold,
                PacketCodecs.registryEntry(RegistryKeys.FLUID), MoldCastingRecipe::getFluid,
                ItemStack.PACKET_CODEC, MoldCastingRecipe::getResult,
                MoldCastingRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<MoldCastingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, MoldCastingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

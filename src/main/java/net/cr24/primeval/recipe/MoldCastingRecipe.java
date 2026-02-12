package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.fluid.FallbackFluid;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

public class MoldCastingRecipe implements CraftingRecipe {

    final Ingredient mold;
    final Holder<Fluid> fluid;
    final ItemStack result;

    public MoldCastingRecipe(Ingredient mold, Holder<Fluid> fluid, ItemStack result) {
        this.result = result;
        this.fluid = fluid;
        this.mold = mold;
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        if (input.ingredientCount() != 1) return false;
        ItemStack stack = input.items().get(0);
        return mold.test(stack) &&
                stack.has(PrimevalDataComponentTypes.FLUID_CONTENTS) &&
                stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS).fluid().is(this.fluid.unwrapKey().get());
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        if (input.ingredientCount() != 1) return ItemStack.EMPTY;
        ItemStack stack = input.items().get(0);
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

    public Holder<Fluid> getFluid() {
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
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.mold);
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<MoldCastingRecipe> {
        private static final MapCodec<MoldCastingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("mold").forGetter((recipe) -> recipe.mold),
                BuiltInRegistries.FLUID.holderByNameCodec().fieldOf("fluid").forGetter((recipe) -> recipe.fluid),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
        ).apply(instance, MoldCastingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, MoldCastingRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, MoldCastingRecipe::getMold,
                ByteBufCodecs.holderRegistry(Registries.FLUID), MoldCastingRecipe::getFluid,
                ItemStack.STREAM_CODEC, MoldCastingRecipe::getResult,
                MoldCastingRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<MoldCastingRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, MoldCastingRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}

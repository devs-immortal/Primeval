package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.util.FluidInput;
import net.cr24.primeval.util.RangedValue;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import java.util.HashMap;
import java.util.Map;

public class AlloyingRecipe implements Recipe<FluidInput> {

    private final Map<Holder<Fluid>, RangedValue> fluidInputs;
    private final Holder<Fluid> fluidResult;

    public AlloyingRecipe(Map<Holder<Fluid>, RangedValue> fluidsIn, Holder<Fluid> fluidResult) {
        this.fluidInputs = fluidsIn;
        this.fluidResult = fluidResult;
    }

    // Matching and Crafting

    @Override
    public boolean matches(FluidInput input, Level world) {
        if (input.isEmpty()) return false;
        Map<Holder<Fluid>, Integer> inventoryFluids = input.getContents();
        int overallAmount = 0;
        for (Holder<Fluid> f : inventoryFluids.keySet()) {
            if (!fluidInputs.containsKey(f)) {
                return false;
            }
            overallAmount += inventoryFluids.get(f);
        }
        for (Holder<Fluid> f : inventoryFluids.keySet()) {
            int stepAmount = inventoryFluids.get(f);
            float percent = ((float)stepAmount) / ((float)overallAmount);
            if (!fluidInputs.get(f).valueIsWithin(percent)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(FluidInput input, HolderLookup.Provider registries) {
        return ItemStack.EMPTY;
    }

    // Accessors

    public Holder<Fluid> getFluidResult() {
        return this.fluidResult;
    }

    public Map<Holder<Fluid>, RangedValue> getFluidInputs() {
        return fluidInputs;
    }


    @Override
    public boolean isSpecial() {
        return true;
    }


    @Override
    public RecipeType<AlloyingRecipe> getType() {
        return PrimevalRecipes.ALLOYING;
    }

    // Crafting Layout

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    // Serializer

    @Override
    public RecipeSerializer<AlloyingRecipe> getSerializer() {
        return PrimevalRecipes.ALLOYING_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        private static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.unboundedMap(BuiltInRegistries.FLUID.holderByNameCodec(), RangedValue.CODEC).fieldOf("fluids").forGetter((recipe) -> recipe.fluidInputs),
                BuiltInRegistries.FLUID.holderByNameCodec().fieldOf("result").forGetter((recipe) -> recipe.fluidResult)
        ).apply(instance, AlloyingRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.map(HashMap::new, ByteBufCodecs.holderRegistry(Registries.FLUID), RangedValue.PACKET_CODEC), AlloyingRecipe::getFluidInputs,
                ByteBufCodecs.holderRegistry(Registries.FLUID), AlloyingRecipe::getFluidResult,
                AlloyingRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<AlloyingRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, AlloyingRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}

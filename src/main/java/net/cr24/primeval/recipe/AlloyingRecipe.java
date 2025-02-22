package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.util.FluidInput;
import net.cr24.primeval.util.RangedValue;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class AlloyingRecipe implements Recipe<FluidInput> {

    private final Map<RegistryEntry<Fluid>, RangedValue> fluidInputs;
    private final RegistryEntry<Fluid> fluidResult;

    public AlloyingRecipe(Map<RegistryEntry<Fluid>, RangedValue> fluidsIn, RegistryEntry<Fluid> fluidResult) {
        this.fluidInputs = fluidsIn;
        this.fluidResult = fluidResult;
    }

    // Matching and Crafting

    @Override
    public boolean matches(FluidInput input, World world) {
        if (input.isEmpty()) return false;
        Map<RegistryEntry<Fluid>, Integer> inventoryFluids = input.getContents();
        int overallAmount = 0;
        for (RegistryEntry<Fluid> f : inventoryFluids.keySet()) {
            if (!fluidInputs.containsKey(f)) {
                return false;
            }
            overallAmount += inventoryFluids.get(f);
        }
        for (RegistryEntry<Fluid> f : inventoryFluids.keySet()) {
            int stepAmount = inventoryFluids.get(f);
            double percent = ((double)stepAmount) / ((double)overallAmount);
            if (!fluidInputs.get(f).valueIsWithin(percent)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(FluidInput input, RegistryWrapper.WrapperLookup registries) {
        return ItemStack.EMPTY;
    }

    // Accessors

    public RegistryEntry<Fluid> getFluidResult() {
        return this.fluidResult;
    }

    public Map<RegistryEntry<Fluid>, RangedValue> getFluidInputs() {
        return fluidInputs;
    }


    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }


    @Override
    public RecipeType<AlloyingRecipe> getType() {
        return PrimevalRecipes.ALLOYING;
    }

    // Crafting Layout

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    // Serializer

    @Override
    public RecipeSerializer<AlloyingRecipe> getSerializer() {
        return PrimevalRecipes.ALLOYING_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        private static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.unboundedMap(Registries.FLUID.getEntryCodec(), RangedValue.CODEC).fieldOf("fluids").forGetter((recipe) -> recipe.fluidInputs),
                Registries.FLUID.getEntryCodec().fieldOf("result").forGetter((recipe) -> recipe.fluidResult)
        ).apply(instance, AlloyingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, AlloyingRecipe> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.map(HashMap::new, PacketCodecs.registryEntry(RegistryKeys.FLUID), RangedValue.PACKET_CODEC), AlloyingRecipe::getFluidInputs,
                PacketCodecs.registryEntry(RegistryKeys.FLUID), AlloyingRecipe::getFluidResult,
                AlloyingRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<AlloyingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, AlloyingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

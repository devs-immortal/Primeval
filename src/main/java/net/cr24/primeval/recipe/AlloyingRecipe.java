package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.fluid.FluidInventory;
import net.cr24.primeval.util.RangedValue;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class AlloyingRecipe implements Recipe<FluidInventory> {

    private final Map<FluidVariant, RangedValue> fluidInputs;
    private final FluidVariant fluidResult;

    public AlloyingRecipe(Map<FluidVariant, RangedValue> fluidsIn, FluidVariant fluidResult) {
        this.fluidInputs = fluidsIn;
        this.fluidResult = fluidResult;
    }

    // Matching and Crafting

    @Override
    public boolean matches(FluidInventory inventory, World world) {
        if (inventory.isEmpty()) return false;
        HashMap<FluidVariant, Integer> inventoryFluids = inventory.getFluids();
        int overallAmount = 0;
        for (FluidVariant f : inventoryFluids.keySet()) {
            if (!fluidInputs.containsKey(f)) {
                return false;
            }
            overallAmount += inventoryFluids.get(f);
        }
        for (FluidVariant f : inventoryFluids.keySet()) {
            int stepAmount = inventoryFluids.get(f);
            double percent = ((double)stepAmount) / ((double)overallAmount);
            if (!fluidInputs.get(f).valueIsWithin(percent)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(FluidInventory input, RegistryWrapper.WrapperLookup registries) {
        return ItemStack.EMPTY;
    }

    // Accessors

    public FluidVariant getFluidResult() {
        return this.fluidResult;
    }

    public Map<FluidVariant, RangedValue> getFluidInputs() {
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
                Codec.unboundedMap(VariantCodecs.FLUID_CODEC, RangedValue.CODEC).fieldOf("fluids").forGetter((recipe) -> recipe.fluidInputs),
                FluidVariant.CODEC.fieldOf("result").forGetter((recipe) -> recipe.fluidResult)
        ).apply(instance, AlloyingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, AlloyingRecipe> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.map(HashMap::new, VariantCodecs.FLUID_PACKET_CODEC, RangedValue.PACKET_CODEC), AlloyingRecipe::getFluidInputs,
                FluidVariant.PACKET_CODEC, AlloyingRecipe::getFluidResult,
                AlloyingRecipe::new
        );

        protected Serializer() {
        }

        public MapCodec<AlloyingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, AlloyingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

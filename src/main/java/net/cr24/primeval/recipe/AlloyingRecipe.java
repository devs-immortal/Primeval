package net.cr24.primeval.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.fluid.FluidInventory;
import net.cr24.primeval.util.RangedValue;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.HashMap;

public class AlloyingRecipe implements Recipe<FluidInventory> {

    private final Identifier id;
    final HashMap<FluidVariant, RangedValue> fluids;
    final FluidVariant fluidResult;

    public AlloyingRecipe(Identifier id, HashMap<FluidVariant, RangedValue> fluids, FluidVariant fluidResult) {
        this.id = id;
        this.fluids = fluids;
        this.fluidResult = fluidResult;
    }

    @Override
    public boolean matches(FluidInventory inventory, World world) {
        if (inventory.isEmpty()) return false;
        HashMap<FluidVariant, Integer> inventoryFluids = inventory.getFluids();
        int overallAmount = 0;
        for (FluidVariant f : inventoryFluids.keySet()) {
            if (!fluids.containsKey(f)) {
                return false;
            }
            overallAmount += inventoryFluids.get(f);
        }
        for (FluidVariant f : inventoryFluids.keySet()) {
            int stepAmount = inventoryFluids.get(f);
            double percent = ((double)stepAmount) / ((double)overallAmount);
            if (!fluids.get(f).valueIsWithin(percent)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack craft(FluidInventory input, RegistryWrapper.WrapperLookup registries) {
        return ItemStack.EMPTY;
    }

    public Identifier getId() {
        return this.id;
    }

    public FluidVariant getFluidResult() {
        return this.fluidResult;
    }

    public HashMap<FluidVariant, RangedValue> getFluidInputs() {
        return this.fluids;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public RecipeSerializer<AlloyingRecipe> getSerializer() {
        return PrimevalRecipes.ALLOYING_SERIALIZER;
    }

    @Override
    public RecipeType<AlloyingRecipe> getType() {
        return PrimevalRecipes.ALLOYING;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        private static final MapCodec<AlloyingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Identifier.CODEC.fieldOf("id").forGetter((recipe) -> recipe.id),
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.id),
                FluidVariant.CODEC.fieldOf("result").forGetter((recipe) -> recipe.fluidResult)
        ).apply(instance, AlloyingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, AlloyingRecipe> PACKET_CODEC = PacketCodec.tuple(
                Identifier.PACKET_CODEC, AlloyingRecipe::getId,
                Ingredient.PACKET_CODEC, AlloyingRecipe::getFluidInputs,
                FluidVariant.PACKET_CODEC, AlloyingRecipe::getFluidResult,
                AlloyingRecipe::new
        );

        protected Serializer() {
        }

        public MapCodec<QuernRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, QuernRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        public Serializer() {
        }

        public AlloyingRecipe read(Identifier identifier, JsonObject jsonObject) {
            JsonArray inputFluids = JsonHelper.getArray(jsonObject, "input");
            HashMap<FluidVariant, RangedValue> ingredientFluids = new HashMap<>();
            for (int i = 0; i < inputFluids.size(); i++) {
                JsonObject item = inputFluids.get(i).getAsJsonObject();
                FluidVariant fluid = FluidVariant.of(Registries.FLUID.get(new Identifier(item.get("fluid").getAsString())));
                double min = item.get("min").getAsDouble();
                double max = item.get("max").getAsDouble();
                ingredientFluids.put(fluid, new RangedValue(max, min));
            }
            JsonObject result = JsonHelper.getObject(jsonObject, "result");
            FluidVariant outputFluid = FluidVariant.of(Registries.FLUID.get(new Identifier(result.get("fluid").getAsString())));
            return new AlloyingRecipe(identifier, ingredientFluids, outputFluid);
        }

        public AlloyingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            HashMap<FluidVariant, RangedValue> ingredientFluids = new HashMap<>();
            int components = packetByteBuf.readInt();
            for (int i = 0; i < components; i++) {
                FluidVariant fluid = FluidVariant.fromPacket(packetByteBuf);
                double min = packetByteBuf.readDouble();
                double max = packetByteBuf.readDouble();
                ingredientFluids.put(fluid, new RangedValue(max, min));
            }
            FluidVariant outputFluid = FluidVariant.fromPacket(packetByteBuf);
            return new AlloyingRecipe(identifier, ingredientFluids, outputFluid);
        }

        public void write(PacketByteBuf packetByteBuf, AlloyingRecipe recipe) {
            packetByteBuf.writeInt(recipe.fluids.size());
            for (FluidVariant f : recipe.fluids.keySet()) {
                f.toPacket(packetByteBuf);
                packetByteBuf.writeDouble(recipe.fluids.get(f).getLower());
                packetByteBuf.writeDouble(recipe.fluids.get(f).getUpper());
            }
            recipe.fluidResult.toPacket(packetByteBuf);
        }
    }
}

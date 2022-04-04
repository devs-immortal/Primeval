package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Collections;

public class MeltingRecipe implements Recipe<Inventory> {

    final Ingredient input;
    final Pair<FluidVariant, Integer> fluidResult;
    private final Identifier id;

    public MeltingRecipe(Identifier id, Ingredient input, Pair<FluidVariant, Integer> fluidResult) {
        this.id = id;
        this.input = input;
        this.fluidResult = fluidResult;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        list.add(this.input);
        return list;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public Pair<FluidVariant, Integer> getFluidResult() {
        return this.fluidResult;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.MELTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PrimevalRecipes.MELTING;
    }

    public static class Serializer implements RecipeSerializer<MeltingRecipe> {
        public Serializer() {
        }

        public MeltingRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient in = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "input"));
            JsonObject result = JsonHelper.getObject(jsonObject, "result");
            FluidVariant fluid = FluidVariant.of(Registry.FLUID.get(new Identifier(result.get("fluid").getAsString())));
            int amount = result.get("amount").getAsInt();
            Pair<FluidVariant, Integer> fluidOut = new Pair<>(fluid, amount);
            return new MeltingRecipe(identifier, in, fluidOut);
        }

        public MeltingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient in = Ingredient.fromPacket(packetByteBuf);
            FluidVariant fluidType = FluidVariant.fromPacket(packetByteBuf);
            int fluidAmount = packetByteBuf.readInt();
            return new MeltingRecipe(identifier, in, new Pair<>(fluidType, fluidAmount));
        }

        public void write(PacketByteBuf packetByteBuf, MeltingRecipe recipe) {
            recipe.input.write(packetByteBuf);
            recipe.fluidResult.getLeft().toPacket(packetByteBuf);
            packetByteBuf.writeInt(recipe.fluidResult.getRight());
        }
    }
}

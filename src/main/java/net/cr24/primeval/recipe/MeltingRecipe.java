package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public class MeltingRecipe implements Recipe<Inventory> {

    final Ingredient input;
    final Pair<Identifier, Integer> fluidResult;
    private final Identifier id;

    public MeltingRecipe(Identifier id, Ingredient input, Pair<Identifier, Integer> fluidResult) {
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
    public Identifier getId() {
        return this.id;
    }

    public Pair<Identifier, Integer> getFluidResult() {
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
            Pair<Identifier, Integer> fluidOut = PrimevalFluids.fluidFromJson(result);
            return new MeltingRecipe(identifier, in, fluidOut);
        }

        public MeltingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient in = Ingredient.fromPacket(packetByteBuf);
            Identifier fluidType = packetByteBuf.readIdentifier();
            int fluidAmount = packetByteBuf.readInt();
            return new MeltingRecipe(identifier, in, new Pair<>(fluidType, fluidAmount));
        }

        public void write(PacketByteBuf packetByteBuf, MeltingRecipe recipe) {
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeIdentifier(recipe.fluidResult.getLeft());
            packetByteBuf.writeInt(recipe.fluidResult.getRight());
        }
    }
}

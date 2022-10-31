package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class QuernRecipe extends SimpleOneToOneRecipe {

    final int wheelDamage;

    public QuernRecipe(Identifier id, Ingredient input, ItemStack result, int wheelDamage) {
        super(id, input, result);
        this.wheelDamage = wheelDamage;
    }

    public int getWheelDamage() {
        return wheelDamage;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(PrimevalBlocks.QUERN);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.QUERN_GRINDING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return PrimevalRecipes.QUERN_GRINDING;
    }

    public static class Serializer implements RecipeSerializer<QuernRecipe> {
        public Serializer() {}

        public QuernRecipe read(Identifier identifier, JsonObject jsonObject) {
            Ingredient in = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "input"));
            ItemStack out = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            int damage = JsonHelper.getInt(jsonObject, "wheelDamage");
            return new QuernRecipe(identifier, in, out, damage);
        }

        public QuernRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Ingredient in = Ingredient.fromPacket(packetByteBuf);
            ItemStack out = packetByteBuf.readItemStack();
            int damage = packetByteBuf.readInt();
            return new QuernRecipe(identifier, in, out, damage);
        }

        public void write(PacketByteBuf packetByteBuf, QuernRecipe recipe) {
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.result);
            packetByteBuf.writeInt(recipe.wheelDamage);
        }
    }
}

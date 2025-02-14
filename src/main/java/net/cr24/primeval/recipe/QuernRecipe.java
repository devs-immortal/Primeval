package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.dynamic.Codecs;

public class QuernRecipe extends SimpleOneToOneRecipe {

    final int wheelDamage;

    public QuernRecipe(Ingredient input, ItemStack result, int wheelDamage) {
        super(input, result);
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
    public RecipeSerializer<QuernRecipe> getSerializer() {
        return PrimevalRecipes.QUERN_GRINDING_SERIALIZER;
    }

    @Override
    public RecipeType<QuernRecipe> getType() {
        return PrimevalRecipes.QUERN_GRINDING;
    }

    public static class Serializer implements RecipeSerializer<QuernRecipe> {
        private static final MapCodec<QuernRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                Codecs.NON_NEGATIVE_INT.fieldOf("wheel_damage").forGetter((recipe) -> recipe.wheelDamage)
        ).apply(instance, QuernRecipe::new));
        private static final PacketCodec<RegistryByteBuf, QuernRecipe> PACKET_CODEC = PacketCodec.tuple(
                Ingredient.PACKET_CODEC, QuernRecipe::getInput,
                ItemStack.PACKET_CODEC, QuernRecipe::getResult,
                PacketCodecs.INTEGER, QuernRecipe::getWheelDamage,
                QuernRecipe::new
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
}

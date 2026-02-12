package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

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
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("wheel_damage").forGetter((recipe) -> recipe.wheelDamage)
        ).apply(instance, QuernRecipe::new));
        private static final StreamCodec<RegistryFriendlyByteBuf, QuernRecipe> PACKET_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, QuernRecipe::getInput,
                ItemStack.STREAM_CODEC, QuernRecipe::getResult,
                ByteBufCodecs.INT, QuernRecipe::getWheelDamage,
                QuernRecipe::new
        );

        public Serializer() {
        }

        public MapCodec<QuernRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, QuernRecipe> streamCodec() {
            return PACKET_CODEC;
        }
    }
}

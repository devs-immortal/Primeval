package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.function.Function;

public abstract class SimpleOneToOneRecipe implements Recipe<SingleStackRecipeInput> {
    protected final Identifier id;
    protected final Ingredient input;
    protected final ItemStack result;

    public SimpleOneToOneRecipe(Identifier id, Ingredient input, ItemStack result) {
            this.id = id;
            this.input = input;
            this.result = result;
    }

    public boolean matches(SingleStackRecipeInput input, World world) {
            return this.input.test(input.item());
    }

    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registries) {
            return this.result.copy();
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    
    public Ingredient getInput() {return this.input;}

    public ItemStack getResult() {
        return this.result.copy();
    }

    public Identifier getId() {
        return this.id;
    }

    
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
    
    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return null; //TODO
    }

    public abstract ItemStack createIcon();


    public abstract RecipeSerializer<? extends SimpleOneToOneRecipe> getSerializer();

    public abstract RecipeType<? extends SimpleOneToOneRecipe> getType();


    @FunctionalInterface
    public interface RecipeFactory<T extends SimpleOneToOneRecipe> {
        T create(Identifier id, Ingredient ingredient, ItemStack result);
    }

    public static class Serializer<T extends SimpleOneToOneRecipe> implements RecipeSerializer<T> {
        private final MapCodec<T> codec;
        private final PacketCodec<RegistryByteBuf, T> packetCodec;

        protected Serializer(RecipeFactory<T> factory) {
            this.codec = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                    Identifier.CODEC.fieldOf("id").forGetter((recipe) -> recipe.id),
                    Ingredient.CODEC.fieldOf("input").forGetter((recipe) -> recipe.input),
                    ItemStack.CODEC.fieldOf("result").forGetter((recipe) -> recipe.result)
            ).apply(instance, factory::create));
            packetCodec = PacketCodec.tuple(
                    Identifier.PACKET_CODEC, SimpleOneToOneRecipe::getId,
                    Ingredient.PACKET_CODEC, SimpleOneToOneRecipe::getInput,
                    ItemStack.PACKET_CODEC, SimpleOneToOneRecipe::getResult,
                    factory::create
            );
        }

        public MapCodec<T> codec() {
            return this.codec;
        }

        public PacketCodec<RegistryByteBuf, T> packetCodec() {
            return this.packetCodec;
        }
    }

}
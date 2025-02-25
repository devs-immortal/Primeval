package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.util.collection.DefaultedList;

import java.util.List;

public class ItemDamagingRecipe extends ShapelessRecipe {
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final List<Ingredient> ingredients;
    static final int DAMAGE_PER_CRAFT = 2;

    public ItemDamagingRecipe(String group, CraftingRecipeCategory category, ItemStack result, List<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public DefaultedList<ItemStack> getRecipeRemainders(CraftingRecipeInput input) {
        DefaultedList<ItemStack> defaultedList = DefaultedList.ofSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            var stack = input.getStackInSlot(i);
            if (stack.contains(DataComponentTypes.DAMAGE) && (stack.getDamage() + DAMAGE_PER_CRAFT) < stack.getMaxDamage()) {
                var copy = stack.copy();
                copy.setDamage(stack.getDamage() + DAMAGE_PER_CRAFT);
                defaultedList.set(i, copy);
                continue;
            }
            defaultedList.set(i, stack.getItem().getRecipeRemainder());
        }

        return defaultedList;
    }

    public static class Serializer implements RecipeSerializer<ItemDamagingRecipe> {
        private static final MapCodec<ItemDamagingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> recipe.group),
                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> recipe.category),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter((recipe) -> recipe.ingredients)
        ).apply(instance, ItemDamagingRecipe::new));

        public static final PacketCodec<RegistryByteBuf, ItemDamagingRecipe> PACKET_CODEC;

        public Serializer() {
        }

        public MapCodec<ItemDamagingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, ItemDamagingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        static {
            PACKET_CODEC = PacketCodec.tuple(
                    PacketCodecs.STRING, (recipe) -> recipe.group,
                    CraftingRecipeCategory.PACKET_CODEC, (recipe) -> recipe.category,
                    ItemStack.PACKET_CODEC, (recipe) -> recipe.result,
                    Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), (recipe) -> recipe.ingredients,
                    ItemDamagingRecipe::new
            );
        }
    }
}

package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import java.util.List;

public class ItemDamagingRecipe extends ShapelessRecipe {
    final String group;
    final CraftingBookCategory category;
    final ItemStack result;
    final List<Ingredient> ingredients;
    static final int DAMAGE_PER_CRAFT = 2;

    public ItemDamagingRecipe(String group, CraftingBookCategory category, ItemStack result, List<Ingredient> ingredients) {
        super(group, category, result, ingredients);
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> defaultedList = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for(int i = 0; i < defaultedList.size(); ++i) {
            var stack = input.getItem(i);
            if (stack.has(DataComponents.DAMAGE) && (stack.getDamageValue() + DAMAGE_PER_CRAFT) < stack.getMaxDamage()) {
                var copy = stack.copy();
                copy.setDamageValue(stack.getDamageValue() + DAMAGE_PER_CRAFT);
                defaultedList.set(i, copy);
                continue;
            }
            defaultedList.set(i, stack.getItem().getCraftingRemainder());
        }

        return defaultedList;
    }

    public static class Serializer implements RecipeSerializer<ItemDamagingRecipe> {
        private static final MapCodec<ItemDamagingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> recipe.group),
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter((recipe) -> recipe.category),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                Ingredient.CODEC.listOf(1, 9).fieldOf("ingredients").forGetter((recipe) -> recipe.ingredients)
        ).apply(instance, ItemDamagingRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ItemDamagingRecipe> PACKET_CODEC;

        public Serializer() {
        }

        public MapCodec<ItemDamagingRecipe> codec() {
            return CODEC;
        }

        public StreamCodec<RegistryFriendlyByteBuf, ItemDamagingRecipe> streamCodec() {
            return PACKET_CODEC;
        }

        static {
            PACKET_CODEC = StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8, (recipe) -> recipe.group,
                    CraftingBookCategory.STREAM_CODEC, (recipe) -> recipe.category,
                    ItemStack.STREAM_CODEC, (recipe) -> recipe.result,
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), (recipe) -> recipe.ingredients,
                    ItemDamagingRecipe::new
            );
        }
    }
}

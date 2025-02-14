package net.cr24.primeval.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.ShapelessCraftingRecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemDamagingRecipe implements CraftingRecipe {
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final List<Ingredient> ingredients;
    final List<Ingredient> damageableIngredients;
    @Nullable
    private IngredientPlacement ingredientPlacement;

    public ItemDamagingRecipe(String group, CraftingRecipeCategory category, ItemStack result, List<Ingredient> ingredients, List<Ingredient> damageableIngredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        this.damageableIngredients = damageableIngredients;
    }

    public RecipeSerializer<ItemDamagingRecipe> getSerializer() {
        return PrimevalRecipes.ITEM_DAMAGING_SERIALIZER;
    }

    public String getGroup() {
        return this.group;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    public IngredientPlacement getIngredientPlacement() {
        if (this.ingredientPlacement == null) {
            this.ingredientPlacement = IngredientPlacement.forShapeless(this.ingredients);
        }

        return this.ingredientPlacement;
    }

    public boolean matches(CraftingRecipeInput craftingRecipeInput, World world) {
        if (craftingRecipeInput.getStackCount() != this.ingredients.size()) {
            return false;
        } else {
            return craftingRecipeInput.size() == 1 && this.ingredients.size() == 1 ? (this.ingredients.getFirst()).test(craftingRecipeInput.getStackInSlot(0)) : craftingRecipeInput.getRecipeMatcher().isCraftable(this, (RecipeMatcher.ItemCallback)null);
        }
    }

    public ItemStack craft(CraftingRecipeInput craftingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return this.result.copy();
    }

    public List<RecipeDisplay> getDisplays() {
        return List.of(new ShapelessCraftingRecipeDisplay(this.ingredients.stream().map(Ingredient::toDisplay).toList(), new SlotDisplay.StackSlotDisplay(this.result), new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)));
    }

    public static class Serializer implements RecipeSerializer<ItemDamagingRecipe> {
        private static final MapCodec<ItemDamagingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter((recipe) -> recipe.group),
                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter((recipe) -> recipe.category),
                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter((recipe) -> recipe.result),
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter((recipe) -> recipe.ingredients),
                Ingredient.CODEC.listOf().fieldOf("damageable_ingredients").forGetter((recipe) -> recipe.damageableIngredients)
        ).apply(instance, ItemDamagingRecipe::new));
        public static final PacketCodec<RegistryByteBuf, ItemDamagingRecipe> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.STRING, (recipe) -> recipe.group,
                CraftingRecipeCategory.PACKET_CODEC, (recipe) -> recipe.category,
                ItemStack.PACKET_CODEC, (recipe) -> recipe.result,
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), (recipe) -> recipe.ingredients,
                Ingredient.PACKET_CODEC.collect(PacketCodecs.toList()), (recipe) -> recipe.damageableIngredients,
                ItemDamagingRecipe::new);

        public Serializer() {
        }

        public MapCodec<ItemDamagingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, ItemDamagingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

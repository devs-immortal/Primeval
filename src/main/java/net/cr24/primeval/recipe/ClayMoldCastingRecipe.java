package net.cr24.primeval.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.fluid.FallbackFluid;
import net.cr24.primeval.item.ClayMoldItem;
import net.cr24.primeval.item.components.PrimevalDataComponentTypes;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;


public class ClayMoldCastingRecipe implements CraftingRecipe {

    private final Item mold;
    private final FluidVariant fluid;
    private final ItemStack result;

    public ClayMoldCastingRecipe(RegistryEntry<Item> mold, FluidVariant fluid, ItemStack result) {
        this.mold = mold.value();
        this.fluid = fluid;
        this.result = result;
    }

    // Matching and Crafting

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        if (input.size() != 1) return false;

        ItemStack stack = input.getStacks().get(0);
        if (stack.getItem() == this.mold && stack.getComponents().contains(PrimevalDataComponentTypes.FLUID_CONTENTS)) {
            var contents = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
            return contents.fluid().equals(this.fluid);
        }

        return false;
    }

    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        if (input.size() != 1) return ItemStack.EMPTY;

        ItemStack stack = input.getStacks().get(0);
        if (stack.getItem() == this.mold && stack.getComponents().contains(PrimevalDataComponentTypes.FLUID_CONTENTS)) {
            var contents = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
            var moldFluid = contents.fluid().getFluid();
            if (contents.amount() == ((ClayMoldItem) stack.getItem()).getCapacity()) {
                return result.copy();
            } else if (moldFluid instanceof FallbackFluid) {
                return new ItemStack(((FallbackFluid) moldFluid).getFallbackItem(), contents.amount() / 1000);
            }
        }

        return ItemStack.EMPTY;


    }

    // Accessors

    public Item getMoldItem() {
        return this.mold;
    }

    public RegistryEntry<Item> getMoldItemRegistryEntry() {
        return this.mold.getRegistryEntry();
    }

    public FluidVariant getFluid() {
        return this.fluid;
    }

    public ItemStack getResult() {
        return this.result;
    }

    // Crafting Layout

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.NONE;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    // Serializer

    @Override
    public RecipeSerializer<ClayMoldCastingRecipe> getSerializer() {
        return PrimevalRecipes.CLAY_MOLD_CASTING_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<ClayMoldCastingRecipe> {
        private static final MapCodec<ClayMoldCastingRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                Item.ENTRY_CODEC.fieldOf("mold").forGetter(ClayMoldCastingRecipe::getMoldItemRegistryEntry),
                FluidVariant.CODEC.fieldOf("fluid").forGetter(ClayMoldCastingRecipe::getFluid),
                ItemStack.CODEC.fieldOf("result").forGetter(ClayMoldCastingRecipe::getResult)
        ).apply(instance, ClayMoldCastingRecipe::new));
        private static final PacketCodec<RegistryByteBuf, ClayMoldCastingRecipe> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.registryEntry(RegistryKeys.ITEM), ClayMoldCastingRecipe::getMoldItemRegistryEntry,
                VariantCodecs.FLUID_PACKET_CODEC, ClayMoldCastingRecipe::getFluid,
                ItemStack.PACKET_CODEC, ClayMoldCastingRecipe::getResult,
                ClayMoldCastingRecipe::new
        );

        protected Serializer() {
        }

        public MapCodec<ClayMoldCastingRecipe> codec() {
            return CODEC;
        }

        public PacketCodec<RegistryByteBuf, ClayMoldCastingRecipe> packetCodec() {
            return PACKET_CODEC;
        }
    }
}

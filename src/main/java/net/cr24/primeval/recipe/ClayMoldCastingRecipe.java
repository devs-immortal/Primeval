package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.fluid.FallbackFluid;
import net.cr24.primeval.item.ClayMoldItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;


public class ClayMoldCastingRecipe implements CraftingRecipe {
    private final Identifier id;
    private final Item mold;
    private final FluidVariant fluid;
    private final ItemStack result;

    public ClayMoldCastingRecipe(Identifier id, Item mold, FluidVariant fluid, ItemStack result) {
        this.id = id;
        this.mold = mold;
        this.fluid = fluid;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingRecipeInput input, World world) {
        ItemStack stack = null;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack2 = inventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (stack != null) return false;
            if (itemStack2.getItem() instanceof ClayMoldItem) {
                stack = itemStack2;
            } else {
                return false;
            }
        }
        if (stack == null) return false;

        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound fluidNbt = nbt.getCompound("Fluid");
        Item moldItem = stack.getItem();
        FluidVariant moldFluid = FluidVariant.fromNbt(fluidNbt);
        int moldFluidAmount = fluidNbt.getInt("Amount");

        if (moldFluidAmount < 1000) return false;
        if (moldItem != mold) return false;
        if (moldFluid != fluid) return false;
        return true;
    }

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack moldStack = ItemStack.EMPTY;
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack itemStack2 = inventory.getStack(i);
            if (itemStack2.isEmpty()) continue;
            if (itemStack2.getItem() instanceof ClayMoldItem) {
                moldStack = itemStack2;
                break;
            } else {
                return ItemStack.EMPTY;
            }
        }
        if (moldStack.isEmpty()) return ItemStack.EMPTY;

        NbtCompound nbt = moldStack.getOrCreateNbt();
        NbtCompound fluidNbt = nbt.getCompound("Fluid");
        ClayMoldItem moldItem = (ClayMoldItem) moldStack.getItem();
        Fluid moldFluid = FluidVariant.fromNbt(fluidNbt).getFluid();
        int moldFluidAmount = fluidNbt.getInt("Amount");

        if (moldFluidAmount == moldItem.getCapacity()) {
            return result.copy();
        } else if (moldFluid instanceof FallbackFluid) {
            return new ItemStack(((FallbackFluid) moldFluid).getFallbackItem(), moldFluidAmount / 1000);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.of();
        ItemStack moldStack = new ItemStack(mold);
        NbtCompound nbt = moldStack.getOrCreateNbt().copy();
        NbtCompound nbtF = fluid.toNbt();
        nbtF.putInt("Amount", ((ClayMoldItem) mold).getCapacity());
        nbt.put("Fluid", nbtF);
        ItemStack stackCopy = moldStack.copy();
        stackCopy.setNbt(nbt);
        list.add(Ingredient.ofStacks(stackCopy));
        return list;
    }

    public FluidVariant getFluid() {
        return this.fluid;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }


    @Override
    public ItemStack craft(CraftingRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.CLAY_MOLD_CASTING_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return CraftingRecipe.super.getType();
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return null;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return CraftingRecipeCategory.MISC;
    }

    public static class Serializer implements RecipeSerializer<ClayMoldCastingRecipe> {
        public Serializer() {
        }

        public ClayMoldCastingRecipe read(Identifier identifier, JsonObject jsonObject) {
            JsonObject inputJson = JsonHelper.getObject(jsonObject, "input");
            Item inputMold = Registries.ITEM.get(new Identifier(JsonHelper.getString(inputJson, "mold")));
            FluidVariant inputFluid = FluidVariant.of(Registries.FLUID.get(new Identifier(JsonHelper.getString(inputJson, "fluid"))));
            ItemStack result = ShapedRecipe.outputFromJson(JsonHelper.getObject(jsonObject, "result"));
            return new ClayMoldCastingRecipe(identifier, inputMold, inputFluid, result);
        }

        public ClayMoldCastingRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            Item inputMold = packetByteBuf.readItemStack().getItem();
            FluidVariant inputFluid = FluidVariant.fromPacket(packetByteBuf);
            ItemStack result = packetByteBuf.readItemStack();
            return new ClayMoldCastingRecipe(identifier, inputMold, inputFluid, result);
        }

        public void write(PacketByteBuf packetByteBuf, ClayMoldCastingRecipe recipe) {
            packetByteBuf.writeItemStack(new ItemStack(recipe.mold));
            recipe.fluid.toPacket(packetByteBuf);
            packetByteBuf.writeItemStack(recipe.result);
        }
    }
}

package net.cr24.primeval.recipe;

import com.google.gson.JsonObject;
import net.cr24.primeval.item.ClayMoldItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Pair;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


public class ClayMoldCastingRecipe implements CraftingRecipe {

    private final Identifier id;
    final Item mold;
    final FluidVariant fluid;
    final ItemStack result;

    public ClayMoldCastingRecipe(Identifier id, Item mold, FluidVariant fluid, ItemStack result) {
        this.id = id;
        this.mold = mold;
        this.fluid = fluid;
        this.result = result;
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {

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
        System.out.println("moldFluidAmount: "+moldFluidAmount);
        if (moldFluidAmount != 9000) return false;
        if (moldItem != mold) return false;
        System.out.print("moldItem: "+moldItem + " =");
        System.out.println("mold: "+mold);
        if (moldFluid != fluid) return false;
        System.out.print("moldFluid: "+moldFluid + " =");
        System.out.println("fluid: "+fluid);
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

        return result.copy();
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
        ClayMoldItem.insertFluid(new Pair<>(fluid, 9000), moldStack);
        list.add(Ingredient.ofStacks(moldStack));
        return list;
    }

    public FluidVariant getFluid() {
        return this.fluid;
    }

    public Ingredient getOutputItem() {
        return Ingredient.ofItems(result.getItem());
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.CLAY_MOLD_CASTING_SERIALIZER;
    }

    public static class Serializer implements RecipeSerializer<ClayMoldCastingRecipe> {
        public Serializer() {
        }

        public ClayMoldCastingRecipe read(Identifier identifier, JsonObject jsonObject) {
            JsonObject inputJson = JsonHelper.getObject(jsonObject, "input");
            Item inputMold = Registry.ITEM.get(new Identifier(JsonHelper.getString(inputJson, "mold")));
            FluidVariant inputFluid = FluidVariant.of(Registry.FLUID.get(new Identifier(JsonHelper.getString(inputJson, "fluid"))));
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

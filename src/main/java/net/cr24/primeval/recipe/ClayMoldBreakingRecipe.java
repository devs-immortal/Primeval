package net.cr24.primeval.recipe;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.fluid.PrefixedFluid;
import net.cr24.primeval.item.ClayMoldItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ClayMoldBreakingRecipe extends SpecialCraftingRecipe {
    public ClayMoldBreakingRecipe(Identifier id) {
        super(id);
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
        Fluid fluid = FluidVariant.fromNbt(fluidNbt).getFluid();
        int fluidAmount = fluidNbt.getInt("Amount");
        if (fluidAmount <= 0) return ItemStack.EMPTY;
        if (fluid instanceof PrefixedFluid) {
            String outputString = ((PrefixedFluid) fluid).getPrefix();
            outputString += "_" + ((ClayMoldItem)moldStack.getItem()).getPostfix();
            System.out.println(outputString);
            return new ItemStack(Registry.ITEM.get(PrimevalMain.getId(outputString)));
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PrimevalRecipes.CLAY_MOLD_BREAKING_RECIPE_SERIALIZER;
    }
}

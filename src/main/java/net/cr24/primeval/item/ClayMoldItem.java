package net.cr24.primeval.item;

import net.cr24.primeval.item.tool.VesselItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class ClayMoldItem extends WeightedItem {

    public int capacity;
    private static int MAX_INSERTION_AMOUNT = 9000;

    public ClayMoldItem(Settings settings, Weight weight, Size size, int capacity) {
        super(settings, weight, size, 1);
        this.capacity = capacity;
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else if (slot.getStack().getItem() instanceof VesselItem) {
            return true;
        }
        return false;
    }

    /*
     * Handle inserting fluid into a mold from a source
     * Return the amount of fluid inserted
     */
    public static int insertFluid(Pair<FluidVariant, Integer> fluidPair, ItemStack mold, PlayerEntity player) {

        NbtCompound nbt = mold.getOrCreateNbt();
        NbtCompound originalNbt = nbt.copy();
        FluidVariant incomingFluid = fluidPair.getLeft();
        int incomingAmount = fluidPair.getRight();
        int currentStoredAmount = 0;
        if (nbt.contains("Fluid")) { // If already stored
            NbtCompound fluidNbt = nbt.getCompound("Fluid"); // get fluid nbt
            FluidVariant stored = FluidVariant.fromNbt(fluidNbt);
            int amount = fluidNbt.getInt("Amount");
            if (amount != 0 && !stored.isBlank()) { // If not and not storing empty
                if (stored.getFluid() == incomingFluid.getFluid()) {
                    currentStoredAmount = amount;
                } else {
                    return 0;
                }
            }
        }
        // if not already storing, or we pass the tests above, insert 1000dp of fluid
        int remainingCapacity = ((ClayMoldItem)mold.getItem()).getCapacity() - currentStoredAmount;
        int amountToInsert = Math.min(remainingCapacity, Math.min(incomingAmount, MAX_INSERTION_AMOUNT));
        // build new nbt for mold item
        NbtCompound nbtF = fluidPair.getLeft().toNbt();
        nbtF.putInt("Amount", currentStoredAmount + amountToInsert);
        nbt.put("Fluid", nbtF);

        mold.setNbt(nbt);

        if (player != null) {
            World world = player.getWorld();
            CraftingInventory recipeInventory = new CraftingInventory(new CraftingScreenHandler(0, player.getInventory()), 2, 2);
            recipeInventory.setStack(0, mold);
            Optional<CraftingRecipe> recipe = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, recipeInventory, world);
            if (!recipe.isPresent()) {
                mold.setNbt(originalNbt);
                return 0;
            }
        }

        return amountToInsert;
    }

    public int getCapacity() {
        return this.capacity;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound fluidNbt = nbt.getCompound("Fluid");
        int fluidAmount = fluidNbt.getInt("Amount");
        if (fluidAmount > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", fluidAmount, Text.translatable(
                            "block."+fluidNbt.getString("fluid").replace(':', '.')
                    ))
                    ).formatted(Formatting.GRAY));
        }
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }



}

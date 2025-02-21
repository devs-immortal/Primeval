package net.cr24.primeval.item;

import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import java.util.List;

public class MoldItem extends WeightedItem {

    public int capacity;
    private static final int MAX_INSERTION_AMOUNT = 9000;

    public MoldItem(Weight weight, Size size, int capacity, Settings settings) {
        super(weight, size, 1, settings);
        this.capacity = capacity;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public static Pair<Boolean, PrimevalDataComponentTypes.FluidContentComponent> insertFluid(PrimevalDataComponentTypes.FluidContentComponent incomingFluid, ItemStack mold) {
        var heldFluid = mold.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(RegistryEntry.of(Fluids.EMPTY), 0));
        int moldCapacity = ((MoldItem)mold.getItem()).getCapacity();
        // if mold is empty or holds same fluid and still has room
        if (heldFluid.fluid().value() == Fluids.EMPTY || (heldFluid.fluid() == incomingFluid.fluid() && heldFluid.amount() < moldCapacity)) {
            int amountToInsert = Math.min(moldCapacity - heldFluid.amount(), Math.min(MAX_INSERTION_AMOUNT, incomingFluid.amount()));
            mold.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), heldFluid.amount() + amountToInsert));
            int remainingVesselAmount = incomingFluid.amount() - amountToInsert;
            if (remainingVesselAmount == 0) {
                return new Pair<>(true, null);
            } else {
                return new Pair<>(true, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), remainingVesselAmount));
            }
        } else {
            // indicates could not fill
            return new Pair<>(false, null);
        }
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(RegistryEntry.of(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", contents.amount(), Text.translatable(
                            "block." + contents.fluid().getIdAsString().replace(':', '.')
                    ))).formatted(Formatting.GRAY));
        }
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }
}

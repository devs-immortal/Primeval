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
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

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

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(FluidVariant.of(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", contents.amount(), Text.translatable(
                            contents.fluid().getRegistryEntry().getIdAsString()
                    ))
                    ).formatted(Formatting.GRAY));
        }
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }
}

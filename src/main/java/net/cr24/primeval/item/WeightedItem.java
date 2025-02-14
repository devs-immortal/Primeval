package net.cr24.primeval.item;

import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeightedItem extends Item implements IWeightedItem {

    protected final Weight weight;
    protected final Size size;

    public WeightedItem(Weight weight, Size size, Settings settings) {
        super(settings.maxCount(size.getStackSize()));
        this.weight = weight;
        this.size = size;
    }

    // Constructor without size-dependent max-count
    public WeightedItem(Weight weight, Size size, int stackSize, Settings settings) {
        super(settings.maxCount(stackSize));
        this.weight = weight;
        this.size = size;
    }

    public WeightedItem(Settings settings, Weight weight, Size size, boolean b) {
        super(settings);
        this.weight = weight;
        this.size = size;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public Size getSize() {
        return size;
    }
}

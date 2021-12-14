package net.cr24.primeval.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeightedItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public WeightedItem(Settings settings, Weight weight, Size size) {
        super(settings.maxCount(size.getStackSize()));
        this.weight = weight;
        this.size = size;
    }

    // Constructor without size-dependent max-count
    public WeightedItem(Settings settings, Weight weight, Size size, int stackSize) {
        super(settings.maxCount(stackSize));
        this.weight = weight;
        this.size = size;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add((new TranslatableText("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));

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

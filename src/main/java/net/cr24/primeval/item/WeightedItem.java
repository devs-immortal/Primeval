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

public class WeightedItem extends Item {

    private final Weight weight;
    private final Size size;

    public WeightedItem(Settings settings, Weight weight, Size size) {
        super(settings);
        this.weight = weight;
        this.size = size;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add((new TranslatableText("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));

    }
}
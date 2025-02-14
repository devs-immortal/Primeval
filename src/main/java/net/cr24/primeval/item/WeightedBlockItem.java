package net.cr24.primeval.item;

import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeightedBlockItem extends BlockItem implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public WeightedBlockItem(Block block, Weight weight, Size size, Settings settings) {
        super(block, settings.maxCount(size.getStackSize()));
        this.weight = weight;
        this.size = size;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
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

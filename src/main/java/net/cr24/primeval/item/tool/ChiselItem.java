package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;
import java.util.function.Consumer;

public class ChiselItem extends Item implements IWeightedItem {
    private final Weight weight;
    private final Size size;

    public ChiselItem(ToolMaterial material, float attackDamage, float attackSpeed, Weight weight, Size size, Settings settings) {
        super(settings.tool(material, PrimevalTags.Blocks.MINEABLE_CHISEL, attackDamage, attackSpeed, 0.0F));
        this.weight = weight;
        this.size = size;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        return super.getMiningSpeed(stack, state) * 0.5f;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
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

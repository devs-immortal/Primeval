package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class PrimevalPickaxeItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public PrimevalPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Weight weight, Size size, net.minecraft.item.Item.Settings settings) {
        super(settings.tool(material, BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.0F));
        this.weight = weight;
        this.size = size;
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

package net.cr24.primeval.item;

import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.List;
import java.util.function.Consumer;

public class WeightedItem extends Item implements IWeightedItem {

    protected final Weight weight;
    protected final Size size;

    public WeightedItem(Weight weight, Size size, Item.Properties settings) {
        super(settings.stacksTo(size.getStackSize()));
        this.weight = weight;
        this.size = size;
    }

    // Constructor without size-dependent max-count
    public WeightedItem(Weight weight, Size size, int stackSize, Item.Properties settings) {
        super(settings.stacksTo(stackSize));
        this.weight = weight;
        this.size = size;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept((Component.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).withStyle(ChatFormatting.GRAY));
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

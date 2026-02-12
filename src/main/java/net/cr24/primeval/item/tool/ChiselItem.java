package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Consumer;

public class ChiselItem extends Item implements IWeightedItem {
    private final Weight weight;
    private final Size size;

    public ChiselItem(ToolMaterial material, float attackDamage, float attackSpeed, Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(settings.tool(material, PrimevalTags.Blocks.MINEABLE_CHISEL, attackDamage, attackSpeed, 0.0F));
        this.weight = weight;
        this.size = size;
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) * 0.5f;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
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

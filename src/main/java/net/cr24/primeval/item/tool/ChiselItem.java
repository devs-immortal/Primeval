package net.cr24.primeval.item.tool;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChiselItem extends MiningToolItem implements IWeightedItem {
    private final Weight weight;
    private final Size size;

    public ChiselItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings, Weight weight, Size size) {
        super(material, PrimevalBlockTags.MINEABLE_CHISEL, attackDamage, attackSpeed, settings);
        this.weight = weight;
        this.size = size;
    }

    public float getMiningSpeed(ItemStack stack, BlockState state) {
        return super.getMiningSpeed(stack, state) * 0.5f;
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
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

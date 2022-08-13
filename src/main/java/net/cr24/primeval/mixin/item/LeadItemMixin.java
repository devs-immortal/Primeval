package net.cr24.primeval.mixin.item;

import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LeadItem;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(LeadItem.class)
public class LeadItemMixin {

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add((Text.translatable("⚖ ").append(Weight.LIGHT.getText()).append(" ⤧ ").append(Size.SMALL.getText())).formatted(Formatting.GRAY));

    }
}

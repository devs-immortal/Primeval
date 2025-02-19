package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public class VesselItem extends BundleItem implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public VesselItem(Weight weight, Size size, Settings settings) {
        super(settings.maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT));
        this.weight = weight;
        this.size = size;
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        PrimevalDataComponentTypes.FluidContentComponent fluidContentComponent = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
        // no fluid
        if (fluidContentComponent == null) {
            return super.onStackClicked(stack, slot, clickType, player);
        } else { // yes fluid
            ItemStack itemStack = slot.getStack();
            if (itemStack.getItem() instanceof MoldItem) {
                var maybeContents = MoldItem.insertFluid(fluidContentComponent, itemStack);
                if (maybeContents.getLeft()) { // fluid was inserted
                    if (maybeContents.getRight() == null) {
                        player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 0.4F, 1.8F + player.getWorld().getRandom().nextFloat() * 0.4F);
                    } else {
                        player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 0.4F, 0.7F + player.getWorld().getRandom().nextFloat() * 0.4F);
                    }
                    stack.set(PrimevalDataComponentTypes.FLUID_CONTENTS, maybeContents.getRight());
                    return true;
                } else { // fluid was not inserted
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        PrimevalDataComponentTypes.FluidContentComponent fluidContentComponent = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
        if (fluidContentComponent == null) {
            return super.onClicked(stack, otherStack, slot, clickType, player, cursorStackReference);
        } else {
            return false;
        }
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return stack.contains(PrimevalDataComponentTypes.FLUID_CONTENTS) ? Optional.empty() : super.getTooltipData(stack);
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(FluidVariant.of(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", contents.amount(), Text.translatable(
                            "block." + contents.fluid().getRegistryEntry().getIdAsString().replace(':', '.')
                    ))
                    ).formatted(Formatting.GRAY));
        }
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }

    @Override
    public Weight getWeight() {
        return this.weight;
    }

    @Override
    public Size getSize() {
        return this.size;
    }

}

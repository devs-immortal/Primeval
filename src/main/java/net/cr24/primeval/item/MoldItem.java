package net.cr24.primeval.item;

import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MoldItem extends WeightedItem {

    final int capacity;
    public final TagKey<Fluid> validFluids;
    private static final int MAX_INSERTION_AMOUNT = 9000;

    public MoldItem(Weight weight, Size size, int capacity, TagKey<Fluid> validFluids, net.minecraft.world.item.Item.Properties settings) {
        super(weight, size, 1, settings);
        this.capacity = capacity;
        this.validFluids = validFluids;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public static Tuple<Boolean, PrimevalDataComponentTypes.FluidContentComponent> insertFluid(PrimevalDataComponentTypes.FluidContentComponent incomingFluid, ItemStack stack) {
        if (stack.getItem() instanceof MoldItem mold) {
            var heldFluid = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(Holder.direct(Fluids.EMPTY), 0));
            int moldCapacity = mold.getCapacity();
            // if fluid is valid to enter and either the mold is empty or it holds the same fluid and still has room
            if (mold.fluidIsValid(incomingFluid.fluid()) && (heldFluid.fluid().value() == Fluids.EMPTY || (heldFluid.fluid() == incomingFluid.fluid() && heldFluid.amount() < moldCapacity))) {
                int amountToInsert = Math.min(moldCapacity - heldFluid.amount(), Math.min(MAX_INSERTION_AMOUNT, incomingFluid.amount()));
                stack.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), heldFluid.amount() + amountToInsert));
                int remainingVesselAmount = incomingFluid.amount() - amountToInsert;
                if (remainingVesselAmount == 0) {
                    return new Tuple<>(true, null);
                } else {
                    return new Tuple<>(true, new PrimevalDataComponentTypes.FluidContentComponent(incomingFluid.fluid(), remainingVesselAmount));
                }
            }
        }
        // indicates could not fill
        return new Tuple<>(false, null);
    }

    public boolean fluidIsValid(Holder<Fluid> inFluid) {
        return inFluid.is(validFluids);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(Holder.direct(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            textConsumer.accept(
                    (Component.translatable("text.primeval.fluid.contains", contents.amount(), Component.translatable(
                            "block." + contents.fluid().getRegisteredName().replace(':', '.')
                    ))).withStyle(ChatFormatting.GRAY));
        }
    }
}

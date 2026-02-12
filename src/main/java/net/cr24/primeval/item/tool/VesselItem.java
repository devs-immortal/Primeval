package net.cr24.primeval.item.tool;

import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.recipe.MeltingRecipe;
import net.cr24.primeval.util.FluidInput;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Consumer;

public class VesselItem extends BundleItem implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public VesselItem(Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(settings.stacksTo(1).component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY));
        this.weight = weight;
        this.size = size;
    }

    public boolean isBarVisible(ItemStack stack) {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        PrimevalDataComponentTypes.FluidContentComponent fluidContentComponent = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
        // no fluid
        if (fluidContentComponent == null) {
            return super.overrideStackedOnOther(stack, slot, clickType, player);
        } else { // yes fluid
            ItemStack itemStack = slot.getItem();
            if (itemStack.getItem() instanceof MoldItem) {
                var maybeContents = MoldItem.insertFluid(fluidContentComponent, itemStack);
                if (maybeContents.getA()) { // fluid was inserted
                    if (maybeContents.getB() == null) {
                        player.playSound(SoundEvents.BUCKET_FILL_LAVA, 0.4F, 1.8F + player.level().getRandom().nextFloat() * 0.4F);
                    } else {
                        player.playSound(SoundEvents.BUCKET_FILL_LAVA, 0.4F, 0.7F + player.level().getRandom().nextFloat() * 0.4F);
                    }
                    stack.set(PrimevalDataComponentTypes.FLUID_CONTENTS, maybeContents.getB());
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
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        PrimevalDataComponentTypes.FluidContentComponent fluidContentComponent = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
        if (fluidContentComponent == null) {
            return super.overrideOtherStackedOnMe(stack, otherStack, slot, clickType, player, cursorStackReference);
        } else {
            return false;
        }
    }

    public static ItemStack processItem(ItemStack vessel, ServerLevel world, RecipeManager.CachedCheck<SingleRecipeInput, MeltingRecipe> meltingMatchGetter, RecipeManager.CachedCheck<FluidInput, AlloyingRecipe> alloyMatchGetter) {
        // fluids from contents
        Map<Holder<Fluid>, Integer> fluids = new HashMap<>();
        int overallFluid = 0;

        if (vessel.has(DataComponents.BUNDLE_CONTENTS)) {
            var contents = vessel.get(DataComponents.BUNDLE_CONTENTS).items();
            for (ItemStack inputItem : contents) {
                SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(inputItem);
                var meltingRecipe = meltingMatchGetter.getRecipeFor(singleStackRecipeInput, world);
                if (meltingRecipe.isPresent()) {
                    var r = meltingRecipe.get().value();
                    if (fluids.containsKey(r.getFluidResult())) {
                        //System.out.println("adding " + r.getFluidAmount() * inputItem.getCount() + " to " + r.getFluidResult() + " (already contains " + fluids.get(r.getFluidResult()) + ")");
                        fluids.put(r.getFluidResult(), fluids.get(r.getFluidResult()) + r.getFluidAmount() * inputItem.getCount());
                    } else {
                        //System.out.println("adding " + r.getFluidAmount() * inputItem.getCount() + " to " + r.getFluidResult() + " (NEW)");
                        fluids.put(r.getFluidResult(), r.getFluidAmount() * inputItem.getCount());
                    }
                    overallFluid += r.getFluidAmount() * inputItem.getCount();
                }
            }
        }
        //System.out.println(fluids);

        // output fluid
        Holder<Fluid> resultFluid;
        if (fluids.size() == 1) {
            resultFluid = fluids.keySet().stream().findFirst().get();
            //System.out.println("ONLY ONE FLUID");
        } else {
            //System.out.println("ATTEMPTING ALLOY");
            FluidInput input = new FluidInput(fluids);
            Optional<AlloyingRecipe> recipe = alloyMatchGetter.getRecipeFor(input, world).map(RecipeHolder::value);
            if (recipe.isPresent()) {
                resultFluid = recipe.get().getFluidResult();
            } else {
                resultFluid = PrimevalFluids.MOLTEN_BOTCHED_ALLOY.builtInRegistryHolder();
            }
        }
        //System.out.println("result = " + resultFluid + "  overallFluid = " + overallFluid);

        vessel.remove(DataComponents.BUNDLE_CONTENTS);
        vessel.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(resultFluid, overallFluid));
        return vessel;
    }

    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return stack.has(PrimevalDataComponentTypes.FLUID_CONTENTS) ? Optional.empty() : super.getTooltipImage(stack);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept((Component.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).withStyle(ChatFormatting.GRAY));
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(Holder.direct(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            textConsumer.accept(
                    (Component.translatable("text.primeval.fluid.contains", contents.amount(), Component.translatable(
                            "block." + contents.fluid().getRegisteredName().replace(':', '.')
                    ))).withStyle(ChatFormatting.GRAY));
        }
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

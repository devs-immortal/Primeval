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
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.BundleTooltipData;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;

import javax.swing.text.html.Option;
import java.util.*;

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

    public static ItemStack processItem(ItemStack vessel, ServerWorld world, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, MeltingRecipe> meltingMatchGetter, ServerRecipeManager.MatchGetter<FluidInput, AlloyingRecipe> alloyMatchGetter) {
        // fluids from contents
        Map<RegistryEntry<Fluid>, Integer> fluids = new HashMap<>();
        int overallFluid = 0;

        if (vessel.contains(DataComponentTypes.BUNDLE_CONTENTS)) {
            var contents = vessel.get(DataComponentTypes.BUNDLE_CONTENTS).iterate();
            for (ItemStack inputItem : contents) {
                SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(inputItem);
                var meltingRecipe = meltingMatchGetter.getFirstMatch(singleStackRecipeInput, world);
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
        RegistryEntry<Fluid> resultFluid;
        if (fluids.size() == 1) {
            resultFluid = fluids.keySet().stream().findFirst().get();
            //System.out.println("ONLY ONE FLUID");
        } else {
            //System.out.println("ATTEMPTING ALLOY");
            FluidInput input = new FluidInput(fluids);
            Optional<AlloyingRecipe> recipe = alloyMatchGetter.getFirstMatch(input, world).map(RecipeEntry::value);
            if (recipe.isPresent()) {
                resultFluid = recipe.get().getFluidResult();
            } else {
                resultFluid = PrimevalFluids.MOLTEN_BOTCHED_ALLOY.getRegistryEntry();
            }
        }
        //System.out.println("result = " + resultFluid + "  overallFluid = " + overallFluid);

        vessel.remove(DataComponentTypes.BUNDLE_CONTENTS);
        vessel.set(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(resultFluid, overallFluid));
        return vessel;
    }

    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return stack.contains(PrimevalDataComponentTypes.FLUID_CONTENTS) ? Optional.empty() : super.getTooltipData(stack);
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var contents = stack.getOrDefault(PrimevalDataComponentTypes.FLUID_CONTENTS, new PrimevalDataComponentTypes.FluidContentComponent(RegistryEntry.of(Fluids.EMPTY), 0));
        if (contents.amount() > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", contents.amount(), Text.translatable(
                            "block." + contents.fluid().getIdAsString().replace(':', '.')
                    ))).formatted(Formatting.GRAY));
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

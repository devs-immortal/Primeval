package net.cr24.primeval.item.tool;

import net.cr24.primeval.fluid.FluidInventory;
import net.cr24.primeval.fluid.PrimevalFluidUtil;
import net.cr24.primeval.fluid.PrimevalFluids;
import net.cr24.primeval.item.ClayMoldItem;
import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.recipe.MeltingRecipe;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class VesselItem extends BundleItem implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public VesselItem(Settings settings, Weight weight, Size size) {
        super(settings);
        this.weight = weight;
        this.size = size;
    }

    //
    // COPIED BUNDLE METHODS
    //

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else if (slot.getStack().getItem() instanceof ClayMoldItem) {
            ItemStack moldItemStack = slot.getStack();

            NbtCompound nbt = stack.getOrCreateNbt();
            NbtCompound fluidNbt = nbt.getCompound("Fluid");
            int fluidAmount = fluidNbt.getInt("Amount");
            Pair<FluidVariant, Integer> fluidPair = new Pair<>(FluidVariant.fromNbt(fluidNbt), fluidAmount);

            int amountInserted = ClayMoldItem.insertFluid(fluidPair, moldItemStack, player);

            fluidNbt.putInt("Amount", fluidAmount-amountInserted);
            nbt.put("Fluid", fluidNbt);

            stack.setNbt(nbt);

            return true;
        } else if (canAddItem(stack)){
            ItemStack itemStack = slot.getStack();
            if (itemStack.isEmpty()) {
                this.playRemoveOneSound(player);
                removeFirstStack(stack).ifPresent((removedStack) -> addToBundle(stack, slot.insertStack(removedStack)));
            } else if (itemStack.getItem().canBeNested()) {
                int i = (64 - getBundleOccupancy(stack)) / getItemOccupancy(itemStack);
                int j = addToBundle(stack, slot.takeStackRange(itemStack.getCount(), i, player));
                if (j > 0) {
                    this.playInsertSound(player);
                }
            }

            return true;
        }
        return false;
    }

    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType == ClickType.RIGHT && slot.canTakePartial(player)) {
            if (otherStack.isEmpty()) {
                removeFirstStack(stack).ifPresent((itemStack) -> {
                    this.playRemoveOneSound(player);
                    cursorStackReference.set(itemStack);
                });
            } else {
                int i = addToBundle(stack, otherStack);
                if (i > 0) {
                    this.playInsertSound(player);
                    otherStack.decrement(i);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private static int addToBundle(ItemStack bundle, ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem().canBeNested() && canAddItem(bundle)) {
            NbtCompound nbtCompound = bundle.getOrCreateNbt();
            if (!nbtCompound.contains("Items")) {
                nbtCompound.put("Items", new NbtList());
            }

            int i = getBundleOccupancy(bundle);
            int j = getItemOccupancy(stack);
            int k = Math.min(stack.getCount(), (64 - i) / j);
            if (k == 0) {
                return 0;
            } else {
                NbtList nbtList = nbtCompound.getList("Items", 10);
                Optional<NbtCompound> optional = canMergeStack(stack, nbtList);
                if (optional.isPresent()) {
                    NbtCompound nbtCompound2 = optional.get();
                    ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                    itemStack.increment(k);
                    itemStack.writeNbt(nbtCompound2);
                    nbtList.remove(nbtCompound2);
                    nbtList.add(0, nbtCompound2);
                } else {
                    ItemStack nbtCompound2 = stack.copy();
                    nbtCompound2.setCount(k);
                    NbtCompound itemStack = new NbtCompound();
                    nbtCompound2.writeNbt(itemStack);
                    nbtList.add(0, itemStack);
                }

                return k;
            }
        } else {
            return 0;
        }
    }

    private static Optional<NbtCompound> canMergeStack(ItemStack stack, NbtList items) {
        if (stack.isOf(Items.BUNDLE)) {
            return Optional.empty();
        } else {
            Stream<NbtElement> var10000 = items.stream();
            Objects.requireNonNull(NbtCompound.class);
            var10000 = var10000.filter(NbtCompound.class::isInstance);
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).filter((item) -> {
                return ItemStack.canCombine(ItemStack.fromNbt( item), stack);
            }).findFirst();
        }
    }

    private static int getItemOccupancy(ItemStack stack) {
        if (stack.isOf(Items.BUNDLE)) {
            return 4 + getBundleOccupancy(stack);
        } else {
            if ((stack.isOf(Items.BEEHIVE) || stack.isOf(Items.BEE_NEST)) && stack.hasNbt()) {
                NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
                if (nbtCompound != null && !nbtCompound.getList("Bees", 10).isEmpty()) {
                    return 64;
                }
            }

            return 64 / stack.getMaxCount();
        }
    }

    private static int getBundleOccupancy(ItemStack stack) {
        return getBundledStacks(stack).mapToInt((itemStack) -> getItemOccupancy(itemStack) * itemStack.getCount()).sum();
    }

    private static Stream<ItemStack> getBundledStacks(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return Stream.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            Stream<NbtElement> var10000 = nbtList.stream();
            Objects.requireNonNull(NbtCompound.class);
            return var10000.map(NbtCompound.class::cast).map(ItemStack::fromNbt);
        }
    }

    private static Optional<ItemStack> removeFirstStack(ItemStack stack) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        if (!nbtCompound.contains("Items")) {
            return Optional.empty();
        } else {
            NbtList nbtList = nbtCompound.getList("Items", 10);
            if (nbtList.isEmpty()) {
                return Optional.empty();
            } else {
                NbtCompound nbtCompound2 = nbtList.getCompound(0);
                ItemStack itemStack = ItemStack.fromNbt(nbtCompound2);
                nbtList.remove(0);
                if (nbtList.isEmpty()) {
                    stack.removeSubNbt("Items");
                }

                return Optional.of(itemStack);
            }
        }
    }

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8F, 0.8F + entity.getWorld().getRandom().nextFloat() * 0.4F);
    }

    //
    // VESSEL-SPECIFIC METHODS
    //

    public static ItemStack processItems(ItemStack vessel, World world) {
        NbtCompound nbt = vessel.getOrCreateNbt();
        NbtList nbtList = nbt.getList("Items", 10);
        // Create new hashmap to store fluids that will be inserted
        HashMap<FluidVariant, Integer> fluids = new HashMap<>();
        ItemStack itemStack; // Item being melted
        Pair<FluidVariant, Integer> fluidResult; // Resulting fluid from melted item, being <type, quantity>
        FluidVariant fluidType; // Type of fluid result
        int fluidAmount; // Amonut of fluid from melting
        int overallFluid = 0;

        for (NbtElement nbtC : nbtList) {
            // Get itemstack from item to melted
            itemStack = ItemStack.fromNbt((NbtCompound) nbtC);
            // Check if meltable recipe for the item
            Optional<MeltingRecipe> option = world.getRecipeManager().getFirstMatch(PrimevalRecipes.MELTING, new SimpleInventory(itemStack), world);
            if (option.isPresent()) { // if can be melted
                fluidResult = option.get().getFluidResult();
                fluidType = fluidResult.getLeft();
                fluidAmount = fluidResult.getRight();
                overallFluid += fluidAmount*itemStack.getCount();
                if (fluids.containsKey(fluidType)) {
                    fluids.put(fluidType, fluids.get(fluidType) + fluidAmount*itemStack.getCount());
                } else {
                    fluids.put(fluidType, fluidAmount*itemStack.getCount());
                }
            }
        }

        FluidVariant resultFluid;
        if (fluids.size() == 1) {
            resultFluid = (FluidVariant) fluids.keySet().toArray()[0];
        } else {
            Optional<AlloyingRecipe> result = world.getRecipeManager().getFirstMatch(PrimevalRecipes.ALLOYING, new FluidInventory(fluids), world);

            if (result.isPresent()) {
                resultFluid = result.get().getFluidResult();
            } else {
                resultFluid = FluidVariant.of(PrimevalFluids.MOLTEN_BOTCHED_ALLOY);
            }
        }

        if (overallFluid > 0) {
            NbtCompound nbtF = resultFluid.toNbt();
            nbtF.putInt("Amount", overallFluid);
            nbt.put("Fluid", nbtF);
        }
        nbt.put("Items", new NbtList()); // Clear out items

        vessel.setNbt(nbt);
        return vessel;
    }

    public static boolean canAddItem(ItemStack vessel) {
        NbtCompound nbt = vessel.getOrCreateNbt();
        if (nbt.contains("Fluid")) {
            return nbt.getCompound("Fluid").getInt("Amount") <= 0;
        }
        return true;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public Size getSize() {
        return size;
    }

    @Environment(EnvType.CLIENT)
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound fluidNbt = nbt.getCompound("Fluid");
        int fluidAmount = fluidNbt.getInt("Amount");
        if (fluidAmount <= 0) {
            return super.getTooltipData(stack);
        }
        return Optional.empty();
    }

    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound fluidNbt = nbt.getCompound("Fluid");
        int fluidAmount = fluidNbt.getInt("Amount");
        if (fluidAmount > 0) {
            tooltip.add(
                    (Text.translatable("text.primeval.fluid.contains", fluidAmount, new TranslatableTextContent(
                            "block."+fluidNbt.getString("fluid").replace(':', '.')
                    ))
            ).formatted(Formatting.GRAY));
        }
        tooltip.add((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
    }
}

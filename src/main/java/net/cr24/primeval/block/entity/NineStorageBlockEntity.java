package net.cr24.primeval.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class NineStorageBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> inventory;

    protected NineStorageBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = NonNullList.withSize(9, ItemStack.EMPTY);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    public int chooseNonEmptySlot(RandomSource random) {
        this.unpackLootTable(null);
        int i = -1;
        int j = 1;

        for(int k = 0; k < this.inventory.size(); ++k) {
            if (!(this.inventory.get(k)).isEmpty() && random.nextInt(j++) == 0) {
                i = k;
            }
        }

        return i;
    }

    public ItemStack addToFirstFreeSlot(ItemStack stack) {
        int i = this.getMaxStackSize(stack);

        for(int j = 0; j < this.inventory.size(); ++j) {
            ItemStack itemStack = (ItemStack)this.inventory.get(j);
            if (itemStack.isEmpty() || ItemStack.isSameItemSameComponents(stack, itemStack)) {
                int k = Math.min(stack.getCount(), i - itemStack.getCount());
                if (k > 0) {
                    if (itemStack.isEmpty()) {
                        this.setItem(j, stack.split(k));
                    } else {
                        stack.shrink(k);
                        itemStack.grow(k);
                    }
                }

                if (stack.isEmpty()) {
                    break;
                }
            }
        }

        return stack;
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        if (!this.tryLoadLootTable(view)) {
            this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        }
        ContainerHelper.loadAllItems(view, this.inventory);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!this.trySaveLootTable(view)) {
            ContainerHelper.saveAllItems(view, this.inventory);
        }
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new DispenserMenu(syncId, playerInventory, this);
    }
}

package net.cr24.primeval.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public abstract class NineStorageBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;

    protected NineStorageBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return 9;
    }

    public int chooseNonEmptySlot(Random random) {
        this.generateLoot(null);
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
        int i = this.getMaxCount(stack);

        for(int j = 0; j < this.inventory.size(); ++j) {
            ItemStack itemStack = (ItemStack)this.inventory.get(j);
            if (itemStack.isEmpty() || ItemStack.areItemsAndComponentsEqual(stack, itemStack)) {
                int k = Math.min(stack.getCount(), i - itemStack.getCount());
                if (k > 0) {
                    if (itemStack.isEmpty()) {
                        this.setStack(j, stack.split(k));
                    } else {
                        stack.decrement(k);
                        itemStack.increment(k);
                    }
                }

                if (stack.isEmpty()) {
                    break;
                }
            }
        }

        return stack;
    }

    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.readLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory, registries);
        }
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        if (!this.writeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory, registries);
        }
    }

    @Override
    protected DefaultedList<ItemStack> getHeldStacks() {
        return this.inventory;
    }

    @Override
    protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new Generic3x3ContainerScreenHandler(syncId, playerInventory, this);
    }
}

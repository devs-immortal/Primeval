package net.cr24.primeval.block.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class AshPileBlockEntity extends BlockEntity implements Clearable {

    private DefaultedList<ItemStack> inventory;

    public AshPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.ASH_PILE_BLOCK_ENTITY, pos, state);
        inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory, registries);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.inventory, registries);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setItems(ItemStack[] items) {
        for (int i = 0; i < 4; i++) {
            this.inventory.set(i, items[i]);
        }
        this.markDirty();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.inventory, registries);
        return nbtCompound;
    }

    public List<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void clear() {
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }
}

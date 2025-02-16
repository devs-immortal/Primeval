package net.cr24.primeval.block.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class LogPileBlockEntity extends BlockEntity implements Clearable {

    private ItemStack item;

    public LogPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LOG_PILE_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        this.item = ItemStack.fromNbt(registries, nbt.getCompound(("Item"))).orElse(ItemStack.EMPTY);
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.put("Item", this.item.toNbt(registries));
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setItem(ItemStack newItem) {
        ItemStack copy = newItem.copy();
        copy.setCount(1);
        this.item = copy;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Item", this.item.toNbt(registries));
        return nbtCompound;
    }

    public ItemStack getItem() {
        return this.item.copy();
    }

    @Override
    public void clear() {
        item = ItemStack.EMPTY;
    }
}

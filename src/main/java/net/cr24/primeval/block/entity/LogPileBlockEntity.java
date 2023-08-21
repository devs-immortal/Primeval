package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class LogPileBlockEntity extends BlockEntity implements Clearable {

    private ItemStack item;

    public LogPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LOG_PILE_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.item = ItemStack.fromNbt(nbt.getCompound(("Item")));
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Item", this.item.writeNbt(new NbtCompound()));
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
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Item", this.item.writeNbt(new NbtCompound()));
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

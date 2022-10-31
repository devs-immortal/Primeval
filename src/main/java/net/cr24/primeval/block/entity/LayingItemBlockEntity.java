package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class LayingItemBlockEntity extends BlockEntity implements Clearable {

    private ItemStack item;
    private final int randomInt;

    public LayingItemBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LAYING_ITEM_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
        randomInt = (pos.getX() + pos.getY()*2 + pos.getZ()*3)%4;
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
        this.item = newItem;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.put("Item", this.item.writeNbt(new NbtCompound()));
        return nbtCompound;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getRandomInt() {
        return this.randomInt;
    }

    @Override
    public void clear() {
        item = ItemStack.EMPTY;
    }
}

package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Clearable;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class LayingItemBlockEntity extends BlockEntity implements Clearable {

    private ItemStack item;
    private final int randomInt;

    public LayingItemBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LAYING_ITEM_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
        randomInt = (pos.getX() + pos.getY()*2 + pos.getZ()*3)%4;
    }

    protected void readData(ReadView view) {
        super.readData(view);
        this.item = view.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!this.item.isEmpty()) {
            view.put("item", ItemStack.CODEC, this.item);
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setItem(ItemStack newItem) {
        this.item = newItem;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        var writeView = NbtWriteView.create(Primeval.errorReporter(this), registries);
        writeData(writeView);
        return writeView.getNbt();
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

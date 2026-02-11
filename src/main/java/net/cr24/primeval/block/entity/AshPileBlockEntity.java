package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
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

import java.util.List;

public class AshPileBlockEntity extends BlockEntity implements Clearable {

    private DefaultedList<ItemStack> inventory;

    public AshPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.ASH_PILE_BLOCK_ENTITY, pos, state);
        inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }

    protected void readData(ReadView view) {
        super.readData(view);
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
        Inventories.readData(view, this.inventory);
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, this.inventory);
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
        var writeView = NbtWriteView.create(Primeval.errorReporter(this), registries);
        writeData(writeView);
        return writeView.getNbt();
    }

    public List<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void clear() {
        this.inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    }
}

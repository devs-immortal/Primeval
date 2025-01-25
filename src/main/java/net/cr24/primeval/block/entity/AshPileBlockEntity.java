package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;

public class AshPileBlockEntity extends BlockEntity implements Clearable {

    private ItemStack[] inventory;

    public AshPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.ASH_PILE_BLOCK_ENTITY, pos, state);
        inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        for (int i = 0; i < 4; i++) {
            if (nbt.contains(("Item"+i), 10)) {
                this.inventory[i] = ItemStack.fromNbt(registries, nbt.getCompound(("Item"+i))).orElse(ItemStack.EMPTY);
            }
        }
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        for (int i = 0; i < 4; i++) {
            nbt.put(("Item"+i), this.inventory[i].toNbt(registries));
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void setItems(ItemStack[] items) {
        this.inventory = items;
        this.markDirty();
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbtCompound = new NbtCompound();
        for (int i = 0; i < 4; i++) {
            nbtCompound.put(("Item"+i), this.inventory[i].toNbt(registries));
        }
        return nbtCompound;
    }

    public ItemStack[] getItems() {
        return this.inventory;
    }

    @Override
    public void clear() {
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
    }
}

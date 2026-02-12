package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class LayingItemBlockEntity extends BlockEntity implements Clearable {

    private ItemStack item;
    private final int randomInt;

    public LayingItemBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LAYING_ITEM_BLOCK_ENTITY, pos, state);
        item = ItemStack.EMPTY;
        randomInt = (pos.getX() + pos.getY()*2 + pos.getZ()*3)%4;
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.item = view.read("item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!this.item.isEmpty()) {
            view.store("item", ItemStack.CODEC, this.item);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setItem(ItemStack newItem) {
        this.item = newItem;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var writeView = TagValueOutput.createWithContext(Primeval.errorReporter(this), registries);
        saveAdditional(writeView);
        return writeView.buildResult();
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getRandomInt() {
        return this.randomInt;
    }

    @Override
    public void clearContent() {
        item = ItemStack.EMPTY;
    }
}

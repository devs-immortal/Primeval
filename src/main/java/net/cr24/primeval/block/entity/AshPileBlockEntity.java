package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.List;

public class AshPileBlockEntity extends BlockEntity implements Clearable {

    private NonNullList<ItemStack> inventory;

    public AshPileBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.ASH_PILE_BLOCK_ENTITY, pos, state);
        inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.inventory = NonNullList.withSize(4, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(view, this.inventory);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, this.inventory);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void setItems(ItemStack[] items) {
        for (int i = 0; i < 4; i++) {
            this.inventory.set(i, items[i]);
        }
        this.setChanged();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var writeView = TagValueOutput.createWithContext(Primeval.errorReporter(this), registries);
        saveAdditional(writeView);
        return writeView.buildResult();
    }

    public List<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    public void clearContent() {
        this.inventory = NonNullList.withSize(4, ItemStack.EMPTY);
    }
}

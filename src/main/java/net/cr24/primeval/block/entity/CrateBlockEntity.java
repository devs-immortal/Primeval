package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.functional.CrateBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.screen.Primeval3x5ContainerScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class CrateBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> inventory;
    private final ContainerOpenersCounter stateManager;

    public CrateBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.CRATE_BLOCK_ENTITY, pos, state);
        this.inventory = NonNullList.withSize(15, ItemStack.EMPTY);
        this.stateManager = new ContainerOpenersCounter() {
            protected void onOpen(Level world, BlockPos pos, BlockState state) {
                CrateBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
                CrateBlockEntity.this.setOpen(state, true);
            }

            protected void onClose(Level world, BlockPos pos, BlockState state) {
                CrateBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
                CrateBlockEntity.this.setOpen(state, false);
            }

            protected void openerCountChanged(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
            }

            public boolean isOwnContainer(Player player) {
                if (player.containerMenu instanceof Primeval3x5ContainerScreenHandler) {
                    Container inventory = ((Primeval3x5ContainerScreenHandler)player.containerMenu).getInventory();
                    return inventory == CrateBlockEntity.this;
                } else {
                    return false;
                }
            }
        };
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(view)) {
            ContainerHelper.loadAllItems(view, this.inventory);
        }
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!this.trySaveLootTable(view)) {
            ContainerHelper.saveAllItems(view, this.inventory);
        }

    }

    @Override
    public int getContainerSize() {
        return 15;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.primeval.crate");
    }


    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return Primeval3x5ContainerScreenHandler.create(syncId, playerInventory, this);
    }

    @Override
    public void startOpen(ContainerUser user) {
        if (!this.remove && !user.getLivingEntity().isSpectator()) {
            this.stateManager.incrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState(), user.getContainerInteractionRange());
        }
    }

    @Override
    public void stopOpen(ContainerUser user) {
        if (!this.remove && !user.getLivingEntity().isSpectator()) {
            this.stateManager.decrementOpeners(user.getLivingEntity(), this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    public void tick() {
        if (!this.remove) {
            this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        this.level.setBlock(this.getBlockPos(), state.setValue(CrateBlock.OPEN, open), Block.UPDATE_ALL);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        double d = (double)this.worldPosition.getX() + 0.5;
        double e = (double)this.worldPosition.getY() + 0.5;
        double f = (double)this.worldPosition.getZ() + 0.5;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
    }
}

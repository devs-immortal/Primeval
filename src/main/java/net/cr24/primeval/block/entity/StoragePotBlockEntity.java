package net.cr24.primeval.block.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class StoragePotBlockEntity extends NineStorageBlockEntity {

    public StoragePotBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LARGE_POT_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.primeval.large_pot");
    }

}

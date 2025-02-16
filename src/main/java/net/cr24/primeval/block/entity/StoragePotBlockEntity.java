package net.cr24.primeval.block.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class StoragePotBlockEntity extends NineStorageBlockEntity {

    public StoragePotBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.LARGE_POT_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.primeval.large_pot");
    }

}

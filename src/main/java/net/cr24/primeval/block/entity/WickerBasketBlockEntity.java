package net.cr24.primeval.block.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;

public class WickerBasketBlockEntity extends NineStorageBlockEntity {

    public WickerBasketBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.WICKER_BASKET_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.primeval.wicker_basket");
    }

}

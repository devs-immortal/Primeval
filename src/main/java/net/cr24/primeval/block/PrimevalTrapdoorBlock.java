package net.cr24.primeval.block;

import net.minecraft.block.TrapdoorBlock;
import net.minecraft.sound.SoundEvents;

public class PrimevalTrapdoorBlock extends TrapdoorBlock {
    public PrimevalTrapdoorBlock(Settings settings) {
        super(settings, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE);
    }
}

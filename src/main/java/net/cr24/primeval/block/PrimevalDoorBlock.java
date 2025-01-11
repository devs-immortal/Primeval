package net.cr24.primeval.block;

import net.minecraft.block.DoorBlock;
import net.minecraft.sound.SoundEvents;

public class PrimevalDoorBlock extends DoorBlock {
    protected PrimevalDoorBlock(Settings settings) {
        super(settings, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE);
    }
}

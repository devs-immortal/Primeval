package net.cr24.primeval.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class PrimevalPlantBlock extends PlantBlock {
    protected PrimevalPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(PrimevalBlockTags.HEAVY_SOIL) || floor.isIn(PrimevalBlockTags.MEDIUM_SOIL);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}

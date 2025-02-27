package net.cr24.primeval.util;

import net.minecraft.util.math.BlockPos;

public class IntPoint2D {
    private int x;
    private int y;

    public IntPoint2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public BlockPos getOffsetBlockPos(BlockPos originalPos) {
        return new BlockPos(originalPos.getX()+x(), originalPos.getY(), originalPos.getZ()+y());
    }

}

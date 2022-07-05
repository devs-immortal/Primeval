package net.cr24.primeval.util

import net.minecraft.util.math.BlockPos

class IntPoint2D(val x: Int, val y: Int) {
    fun getOffsetBlockPos(originalPos: BlockPos): BlockPos {
        return BlockPos(originalPos.x + x, originalPos.y, originalPos.z + y)
    }
}
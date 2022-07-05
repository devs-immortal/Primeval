package net.cr24.primeval.util

class ShapesUtil {
    companion object {
        fun getCoordinatesInCircle(radius: Int): Array<IntPoint2D> {
            val points = ArrayList<IntPoint2D>(radius)
            val radiusSquared = radius * radius
            for (x in -radius..radius) {
                for (y in -radius..radius) {
                    if (x * x + y * y <= radiusSquared) {
                        points.add(IntPoint2D(x, y))
                    }
                }
            }
            return points.toTypedArray()
        }
    }
}
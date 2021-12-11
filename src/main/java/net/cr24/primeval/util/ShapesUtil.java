package net.cr24.primeval.util;

import java.util.ArrayList;

public class ShapesUtil {

    public static IntPoint2D[] getCoordinatesInCircle(int radius) {
        ArrayList<IntPoint2D> points = new ArrayList<>(radius);
        int radiusSquared = radius * radius;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                if (x*x + y*y <= radiusSquared) {
                    points.add(new IntPoint2D(x, y));
                }
            }
        }
        return points.toArray(new IntPoint2D[points.size()]);
    }

}

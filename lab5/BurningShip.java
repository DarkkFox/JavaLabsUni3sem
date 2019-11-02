package ru.izebit.fifth;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    @Override
    public Rectangle2D.Double getInitialRange(final Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.width = 4;
        range.height = 4;
        return range;
    }

    @Override
    public int numIterations(final double pointReal, final double pointImage) {
        double zReal = 0.0;
        double zImage = 0.0;

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            double real = Math.pow(zReal, 2);
            double image = Math.pow(zImage, 2);

            if (real + image > 4.0)
                return i - 1;

            zImage = Math.abs(2 * zReal * zImage + pointImage);
            zReal = Math.abs(real - image + pointReal);
        }

        return -1;
    }
}

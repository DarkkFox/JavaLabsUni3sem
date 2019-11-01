package ru.izebit.third;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 23.10.2019
 */
public class Mandelbrot extends FractalGenerator {
    private static final int MAX_ITERATIONS = 2000;
    private final Map<Double, Map<Double, Integer>> numbers = new HashMap<>();

    @Override
    public Rectangle2D.Double getInitialRange(final Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
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

            zImage = 2 * zReal * zImage + pointImage;
            zReal = real - image + pointReal;
        }

        return -1;
    }
}

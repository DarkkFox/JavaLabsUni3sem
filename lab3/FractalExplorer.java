package ru.izebit.third;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * @author <a href="mailto:izebit@gmail.com">Artem Konovalov</a> <br/>
 * Date: 23.10.2019
 */
public class FractalExplorer extends JFrame {
    private static final int width = 500;
    private static final int height = 500;

    private final JImageDisplay imageDisplay;
    private final JButton button;
    private final FractalGenerator generator;
    private Rectangle2D.Double range;

    public FractalExplorer(int width, int height) {
        this.imageDisplay = new JImageDisplay(width, height);
        this.button = new JButton("reset");
        this.generator = new Mandelbrot();
        this.range = generator.getInitialRange(new Rectangle2D.Double());
    }

    private void createAndShowGUI() {
        this.setLayout(new BorderLayout());

        imageDisplay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.getWidth(), imageDisplay.getWidth(), e.getX());
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.getHeight(), imageDisplay.getHeight(), e.getY());
                generator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();
            }
        });
        this.add(imageDisplay, BorderLayout.CENTER);

        button.addActionListener(event -> {
            range = generator.getInitialRange(new Rectangle2D.Double());
            imageDisplay.clearImage();
            repaint();
            drawFractal();
        });
        this.add(button, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(width, height + 20));
        this.setTitle("this is a fractal demo");
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }

    private void drawFractal() {
        for (int x = 0; x < imageDisplay.getWidth(); x++) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.getWidth(), imageDisplay.getWidth(), x);
            for (int y = 0; y < imageDisplay.getHeight(); y++) {
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.getHeight(), imageDisplay.getHeight(), y);
                int numIterations = generator.numIterations(xCoord, yCoord);
                imageDisplay.drawPixel(x, y, getColor(numIterations));
            }
        }

        repaint();
    }

    private static int getColor(int numIters) {
        if (numIters < 0)
            return 0;

        float hue = 0.7f + (float) numIters / 200f;
        return Color.HSBtoRGB(hue, 1f, 1f);
    }

    public static void main(String[] args) {
        final FractalExplorer fractalExplorer = new FractalExplorer(width, height);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.drawFractal();
    }
}

package ru.izebit.fifth;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;


public class JImageDisplay extends JComponent {
    private final BufferedImage image;

    public JImageDisplay(int width, int height) {
        this.image = new BufferedImage(width, height, TYPE_INT_RGB);
        super.setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void clearImage() {
        for (int i = 0; i < image.getWidth(); i++)
            for (int j = 0; j < image.getHeight(); j++)
                image.setRGB(i, j, 0);
    }

    public void drawPixel(int x, int y, int rgbColor) {
        this.image.setRGB(x, y, rgbColor);
    }

    public int getImageWidth() {
        return image.getWidth();
    }

    public int getImageHeight() {
        return image.getHeight();
    }

    public BufferedImage getBufferedImage() {
        return image;
    }
}

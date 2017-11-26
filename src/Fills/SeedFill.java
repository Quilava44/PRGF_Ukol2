package Fills;

import Render.Renderer;

import java.awt.image.BufferedImage;

public class SeedFill extends Renderer {

    public SeedFill(BufferedImage img) {

        super(img);

    }

    public void fill(int x, int y, int clr) {
        if (x < img.getWidth() && y < img.getHeight() && x > 0 && y > 0) {
            if (clr == img.getRGB(x, y)) {
                img.setRGB(x, y, 0xff0000);
                fill(x + 1, y, clr);
                fill(x - 1, y, clr);
                fill(x, y + 1, clr);
                fill(x, y - 1, clr);
            }
        }

    }

}

package Fills;

import java.awt.image.BufferedImage;

public class SeedFill {

    private final BufferedImage img;

    public SeedFill(BufferedImage img) {
        this.img = img;
    }

    public void fill(int x, int y, int clr) {
        if (clr == img.getRGB(x,y)) {
            img.setRGB(x,y,0xff0000);
            fill(x+1,y,clr);
            fill(x-1,y,clr);
            fill(x,y+1,clr);
            fill(x,y-1,clr);
        }

    }

}

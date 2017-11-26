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

    /**
     * Seed fill se vzorem
     */


    public void fillPattern(int x, int y, int clr) {
        int [][] clrMat = new int [][]{
                {0xff7d0a,0x0070de,0x0070de,0x0070de,0x0070de,0x0070de,0xff7d0a},
                {0x0070de,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0x0070de},
                {0x0070de,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0x0070de},
                {0x0070de,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0x0070de},
                {0x0070de,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0x0070de},
                {0x0070de,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0xff7d0a,0x0070de},
                {0xff7d0a,0x0070de,0x0070de,0x0070de,0x0070de,0x0070de,0xff7d0a}};

        if (x < img.getWidth() && y < img.getHeight() && x > 0 && y > 0) {
            if (clr == img.getRGB(x, y)) {
                img.setRGB(x,y, clrMat[x % 7][y % 7]);
                fillPattern(x + 1,y,clr);
                fillPattern(x - 1,y,clr);
                fillPattern(x  ,y + 1,clr);
                fillPattern(x  ,y - 1,clr);
            }
        }
    }

}

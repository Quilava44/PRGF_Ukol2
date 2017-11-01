package Render;

import java.awt.image.BufferedImage;

public abstract class Renderer {

    protected final BufferedImage img;

    public Renderer (BufferedImage img) {
        this.img = img;
    }

}

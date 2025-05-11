package main.java.tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BackgroundTile {
    BufferedImage image;
    BufferedImage[] variants;

    public BackgroundTile(int anzVariants){
        if(anzVariants > 0)
            variants = new BufferedImage[anzVariants];
    }

    public void setImage(String name){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/textures/background/"+name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

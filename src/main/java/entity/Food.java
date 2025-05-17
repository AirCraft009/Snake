package main.java.entity;

import main.java.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class Food extends Entity{

    private FoodType type;
    GamePanel gp;

    public Food(int nX, int nY, GamePanel gp){
        this.gp = gp;
        x = nX;
        y =nY;
        getImage();
    }

    public void getImage(){
        try {
            I1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entities/food/apple.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void blit(Graphics2D g2){
        g2.drawImage(I1, x, y, gp.REALTILESIZE, gp.REALTILESIZE, null);
    }
}

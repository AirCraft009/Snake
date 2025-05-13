package main.java.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.util.Objects;

public class Food extends Entity{

    private FoodType type;

    public Food(int nX, int nY){
        x = nX;
        y =nY;
    }

    public void getImage(){
        try {
            I1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entities/food/apple.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void blit(Graphics2D g2){
        g2.drawImage(I1, x, y, 48, 48, null);
    }
}

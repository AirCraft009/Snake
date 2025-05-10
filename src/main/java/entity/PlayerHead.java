package main.java.entity;

import main.java.main.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerHead extends Entity{

    GamePanel gp;
    KeyHandler keyH;

    public PlayerHead(GamePanel gp, KeyHandler KeyH){
        this.gp = gp;
        this.keyH = KeyH;
        getPlayerImages();

    }

    public void setDefault(){
        x = 200;
        y = 400;
        speed  = 8;
    }

    public void getPlayerImages(){
        try {
            I1 = ImageIO.read(getClass().getResourceAsStream("/textures/entities/snake/snake-head-base-up.png"));
            I2 = ImageIO.read(getClass().getResourceAsStream("/textures/entities/snake/snake-head-base-down.png"));
            I3 = ImageIO.read(getClass().getResourceAsStream("/textures/entities/snake/snake-head-base-right.png"));
            I4 = ImageIO.read(getClass().getResourceAsStream("/textures/entities/snake/snake-head-base-left.png"));

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void update(int tick) {
        if(tick%gp.TICKSPEED==0) {
            if (keyH.rightP && gp.currentDir != Direction.Left) {
                gp.currentDir = Direction.Right;
            } else if (keyH.leftP && gp.currentDir != Direction.Right) {
                gp.currentDir = Direction.Left;
            } else if (keyH.upP && gp.currentDir != Direction.Downward) {
                gp.currentDir = Direction.Upward;
            } else if (keyH.downP && gp.currentDir != Direction.Upward) {
                gp.currentDir = Direction.Downward;
            }
        }

        switch (gp.currentDir) {
            case Left:
                x -= speed;
                break;
            case Right:
                x += speed;
                break;
            case Upward:
                y -= speed;
                break;
            case Downward:
                y += speed;
                break;
            case None:
                break;
        }
    }

    public void blit(Graphics g2){
        g2.setColor(Color.white);
        BufferedImage image = switch (gp.currentDir) {
            case Upward -> I1;
            case Downward -> I2;
            case Right -> I3;
            case Left -> I4;
            case None -> I3;
        };
        g2.drawImage(image, x, y, gp.REALTILESIZE, gp.getREALTILESIZE(), null);
    }
}

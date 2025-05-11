package main.java.entity;

import main.java.main.*;
import main.java.entity.EntityType;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerHead extends Entity{

    GamePanel gp;
    KeyHandler keyH;
    public Direction currentDir = Direction.None;
    public Direction prevDir = Direction.None;

    public PlayerHead(GamePanel gp, KeyHandler KeyH){
        type = EntityType.PlayerHead;
        this.gp = gp;
        this.keyH = KeyH;
        getPlayerImages();

    }

    public void setDefault(int x, int y){
        this.x = x;
        this.y = y;
        speed  = gp.baseSpeed*1;
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
        prevDir = currentDir;
        if(tick%gp.TICKSPEED==0) {
            if (keyH.rightP && currentDir != Direction.Left) {
                currentDir = Direction.Right;
            } else if (keyH.leftP && currentDir != Direction.Right) {
                currentDir = Direction.Left;
            } else if (keyH.upP && currentDir != Direction.Downward) {
                currentDir = Direction.Upward;
            } else if (keyH.downP && currentDir != Direction.Upward) {
                currentDir = Direction.Downward;
            }
        }

        switch (currentDir) {
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
        BufferedImage image = switch (currentDir) {
            case Upward -> I1;
            case Downward -> I2;
            case Right, None -> I3;
            case Left -> I4;
        };
        g2.drawImage(image, x, y, gp.REALTILESIZE, gp.getREALTILESIZE(), null);
    }

    public void freezeblit(Graphics2D g2,int px,int py){
        x = px;
        y = py;
        currentDir = prevDir;
        blit(g2);
    }
}

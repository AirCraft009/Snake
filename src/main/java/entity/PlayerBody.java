package main.java.entity;

import main.java.main.GamePanel;
import main.java.main.KeyHandler;

import java.awt.*;

public class PlayerBody extends Entity{

    GamePanel gp;
    public PlayerBody(GamePanel gp){
        this.gp = gp;
        this.x = gp.player.x-48;
        this.y = gp.player.y;
    }

    public void blit(Graphics g2){
        x = gp.player.x-48;
        y = gp.player.y;
        g2.setColor(Color.white);
        g2.fillRect(x, y, gp.REALTILESIZE, gp.REALTILESIZE);

    }


}

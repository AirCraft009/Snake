package main.java.entity;

import main.java.main.GamePanel;
import main.java.tile.TileManager;

import java.awt.*;
import java.util.Random;

public class FoodPlacer {
    private Food[] foods;
    private GamePanel gp;
    private Random rand = new Random();
    private TileManager tm;

    public FoodPlacer(GamePanel gp, int foodLen, TileManager tm){
        this.gp = gp;
        this.tm = tm;
        foods = new Food[foodLen];
        for (int i = 0; i < foodLen; i++) {
            foods[i] = new Food(48*(i+2), 144+48*i, gp);
        }
    }

    public boolean chekEaten(int x, int y){
        for (int i = 0; i < foods.length; i++) {
            if(foods[i].x == x && foods[i].y == y){
                getEaten(i);
                return true;
            }
        }
        return false;
    }

    public void getEaten(int pos){
        do {
            foods[pos].x = rand.nextInt(1, gp.getMAXSCREENCOL()-1)*48;
            foods[pos].y = rand.nextInt(1, gp.getMAXSCREENROW()-1)*48;
        }while (tm.BackgroundMap[foods[pos].y/gp.REALTILESIZE][foods[pos].x/gp.REALTILESIZE] == 3);
    }

    public void blit(Graphics2D g2){
        for (Food food : foods){
            food.blit(g2);
        }
    }
}

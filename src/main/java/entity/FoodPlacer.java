package main.java.entity;

import main.java.main.GamePanel;
import main.java.tile.TileManager;

import java.awt.*;
import java.util.ArrayList;
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
    }

    public void init(Snake snake){
        for (int i = 0; i < foods.length; i++) {
            foods[i] = new Food(0, 0, gp);
            getEaten(i, snake);
        }
    }

    public boolean chekEaten(int x, int y, Snake snake){
        for (int i = 0; i < foods.length; i++) {
            if(foods[i].x == x && foods[i].y == y){
                getEaten(i, snake);
                return true;
            }
        }
        return false;
    }

    public void newRandomCords(int pos){
        Food food = foods[pos];
        food.x = rand.nextInt(1, gp.getMAXSCREENCOL()-1)*48;
        food.y = rand.nextInt(1, gp.getMAXSCREENROW()-1)*48;
    }

    public void getEaten(int pos, Snake snake){
        Food food = foods[pos];
        do {
            newRandomCords(pos);
        }while (tm.BackgroundMap[food.y/gp.REALTILESIZE][food.x/gp.REALTILESIZE] == 3 || snake.snakeAtThatpoint(food.x, food.y));
    }

    public void blit(Graphics2D g2){
        for (Food food : foods){
            food.blit(g2);
        }
    }
}

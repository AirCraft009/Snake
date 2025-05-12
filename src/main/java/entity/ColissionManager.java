package main.java.entity;

import main.java.main.GamePanel;
import main.java.tile.TileManager;

public class ColissionManager {
    GamePanel gp;
    TileManager tm;
    public int[][] field;
    private Snake snake;
    private int snakex;
    private int snakey;


    public ColissionManager(GamePanel gp, TileManager tm, Snake snake) {
        this.snake = snake;
        this.gp = gp;
        this.tm = tm;
        field = tm.BackgroundMap;
        snakex = this.snake.head.x/gp.REALTILESIZE;
        snakey = this.snake.head.y/gp.REALTILESIZE;
    }

    public boolean checkWallColission(){
        snakex = snake.head.x/gp.REALTILESIZE;
        snakey = snake.head.y/gp.REALTILESIZE;
        return field[snakey][snakex] == 3;
    }

    public boolean checkOwnColission(){
        snakex = snake.head.x/gp.REALTILESIZE;
        snakey = snake.head.y/gp.REALTILESIZE;
        for (PlayerBody p1 : snake.body){
            if(p1.type == EntityType.PlayerTail)
                return false;
            if(p1.x/gp.REALTILESIZE == snakex && p1.y/gp.REALTILESIZE == snakey)
                return true;
        }
        return false;
    }
}

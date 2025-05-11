package main.java.entity;

import main.java.main.GamePanel;
import main.java.main.Mode;
import main.java.tile.TileManager;

public class ColissionManager {
    GamePanel gp;
    TileManager tm;
    public int[][] field;
    int snake1x;
    int snake1y;
    int snake2x;
    int snake2y;


    public ColissionManager(GamePanel gp, TileManager tm) {
        this.gp = gp;
        this.tm = tm;
        field = tm.BackgroundMap;
        snake1x = gp.s1.head.x/gp.REALTILESIZE;
        snake1y = gp.s1.head.y/gp.REALTILESIZE;
        if(gp.players == Mode.Double){
            snake2x = gp.s2.head.x/gp.REALTILESIZE;
            snake2y = gp.s2.head.y/gp.REALTILESIZE;
        }
    }

    public void updateField(){
        snake1x = gp.s1.head.x/gp.REALTILESIZE;
        snake1y = gp.s1.head.y/gp.REALTILESIZE;
        if(field[snake1y][snake1x] == 3){
            System.out.println("collision with wall detected");
            gp.pauseGame();
        }
    }
}

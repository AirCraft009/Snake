package main.java.tile;

import main.java.main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.module.ModuleDescriptor;
import java.util.Arrays;
import java.util.Random;

public class TileManager {
    GamePanel gp;
    BackgroundTile[] tiles;
    public int[][] BackgroundMap;

    public TileManager(GamePanel gp, String name){
        this.gp = gp;
        tiles = new BackgroundTile[gp.getMAXSCREENROW()*gp.getMAXSCREENCOL()];
        BackgroundMap = new int[gp.getMAXSCREENROW()][gp.getMAXSCREENCOL()];
        getImages(name);
    }

    public void readMapfile(String mapName){
        try{
            System.out.println(mapName);
            InputStream is = getClass().getResourceAsStream("/map/" + mapName + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            for (int i = 0; i < gp.getMAXSCREENROW(); i++) {
                String line = reader.readLine();
                String[] nums = line.split(" ");
                for (int j = 0; j < nums.length; j++) {
                    try {
                        int tileNum = Integer.parseInt(nums[j].strip());
                        BackgroundMap[i][j] = tileNum;
                    }
                    catch (Exception e){
                        System.err.println("Only numbers and spaces are allowed in map files " + e);
                    }
                }
            }
        }
        catch (Exception e){
            System.err.println("map doesn't exist or another error occured");
            e.printStackTrace();
        }
    }

    public void getImages(String Mapname){
        readMapfile(Mapname);
        int count = 0;
        for (int i = 0; i < gp.getMAXSCREENROW(); i++) {
            for (int j = 0; j < gp.getMAXSCREENCOL(); j++) {
                tiles[i*gp.getMAXSCREENCOL()+j] = new BackgroundTile(0);
                tiles[i*gp.getMAXSCREENCOL()+j].setImage(TileType.getFileNameByIndex(BackgroundMap[i][j]));
            }
        }
    }

    public void blit(Graphics2D g2){
        for (int i = 0; i < gp.getMAXSCREENROW(); i++) {
            for (int j = 0; j < gp.getMAXSCREENCOL(); j++) {
                g2.drawImage(tiles[i*gp.getMAXSCREENCOL()+j].image, j*48, i*48, gp.REALTILESIZE, gp.REALTILESIZE, null);
            }
        }
    }
}

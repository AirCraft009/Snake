package main.java.main;
import main.java.entity.*;

import javax.swing.*;
import java.awt.*;


/**
 * Diese Klasse ist das Herzstück des Spiels in ihr rennt die Game-Clock sowie alle anderen
 * tick updates
 */

public class GamePanel extends JPanel implements Runnable{

    /**
     * implents Runnable bedeutet, dass ein Thread gestartet wird und somit die ganze Zeit im
     * Hintergrung läuft das ist die Game clock.
     * Die run() Methode wird immer aufgerufen wird der Thread gestartet
     */

    //SCREEN SETTINGS
    private final int TILESIZE = 16;
    /**
     * while the graphic tiles are 16x16
     * they are upscaled because 16x16 pixels
     * on a 1080p screen is too small
     */
    private final int SECONDTONANO = 1000000000;
    private final int UPSCALE = 3;
    public final int REALTILESIZE = TILESIZE*UPSCALE; //48x48
    private final int MAXSCREENCOL = 16;
    private final int MAXSCREENROW = 12;
    private final int SCREENWIDTH = REALTILESIZE * MAXSCREENCOL;
    private final int SCREENHEIGHT = REALTILESIZE * MAXSCREENROW;

    //TIME SETTINGS
    private int FPS = 60;
    private long drawDelay  = SECONDTONANO/FPS;
    private long lastFrame = System.nanoTime();
    private long currentTime;
    private double deltaTime = 0;
    public double fpsCount = 0;
    private double timer;

    //POSITIONAL SETTINGS
    public Direction currentDir = Direction.Left;


    public KeyHandler keyH = new KeyHandler();
    public PlayerHead player = new PlayerHead(this, keyH);
    private Thread gameThread;


    public GamePanel(){
        setFocusable(true);
        setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        player.setDefault();
        addKeyListener(keyH);
    }

    public void StartGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void update(){
        player.update();
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
        player.blit(g2);
        g2.dispose();
    }

    @Override
    public void run() {
        /**
         * In this loop two things will happen first of all there is an update-cycle
         * meaning, that it updates where everything is for example the player.
         *
         * 2. The drawing stage in this part of the loop everything thing that is updated is deleted and drawn again
         */
        while (gameThread != null){
            currentTime = System.nanoTime();
            deltaTime += (double) (currentTime - lastFrame) / drawDelay;
            timer += (double) (currentTime - lastFrame);
            lastFrame = currentTime;
            if(deltaTime >= 1) {
                update();
                repaint();
                deltaTime--;
                fpsCount ++;
            }
            if (timer >= SECONDTONANO){
                System.out.println(fpsCount);
                fpsCount = 0;
                timer = 0;
            }

        }
    }



    public int getFps() {
        return FPS;
    }

    public void setFps(int fps) {
        this.FPS = fps;
        drawDelay = 1000000000/fps;
    }

    public int getREALTILESIZE() {
        return REALTILESIZE;
    }

    public int getUPSCALE() {
        return UPSCALE;
    }

    public int getTILESIZE() {
        return TILESIZE;
    }

    public int getMAXSCREENCOL() {
        return MAXSCREENCOL;
    }

    public int getMAXSCREENROW() {
        return MAXSCREENROW;
    }

    public int getSCREENWIDTH() {
        return SCREENWIDTH;
    }

    public int getSCREENHEIGHT() {
        return SCREENHEIGHT;
    }
}

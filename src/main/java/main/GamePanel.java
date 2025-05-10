package main.java.main;
import main.java.entity.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


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
    public int TICKSPEED = 1;
    public int ChangeRate;
    private final int SECONDTONANO = 1000000000;
    private final int UPSCALE = 3;
    public final int REALTILESIZE = TILESIZE*UPSCALE; //48x48
    private final int MAXSCREENCOL = 17;
    private final int MAXSCREENROW = 15;
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
    public Direction currentDir = Direction.None;
    public int currTick = 0;
    public int baseSpeed = 48;
    public Mode players;

    public Difficulty diff;
    public KeyHandler keyH = new KeyHandler();
    public Snake s1;
    public Snake s2;
    private Thread gameThread;


    public GamePanel(Difficulty d, Mode mode) throws IOException {
        ChangeRate = FPS*baseSpeed/10;
        diff = d;
        //s1 = new Snake(this, keyH, 144, 144, 3);
        if(mode == Mode.Double)
            s2 = new Snake(this, keyH, 144, 200, 4);
        setFocusable(true);
        setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyH);
    }

    public void StartGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void update(){
       // s1.update(currTick);
        if(players == Mode.Double)
            s2.update(currTick);
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
       // s1.blit(g2);
        if(players == Mode.Double){
            s2.blit(g2);
        }
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
                currTick ++;
                deltaTime--;
                fpsCount ++;
            }
            if (timer >= SECONDTONANO){
                //System.out.println(fpsCount);
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

    public int getTICKSPEED() {
        return TICKSPEED;
    }

    public void setTICKSPEED(int TICKSPEED) {
        this.TICKSPEED = TICKSPEED;
    }
}

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
    private Direction currentDir = Direction.Left;
    private final int REALTILESIZE = TILESIZE*UPSCALE; //40x40
    private final int MAXSCREENCOL = 16;
    private final int MAXSCREENROW = 12;
    private final int SCREENWIDTH = REALTILESIZE * MAXSCREENCOL;
    private final int SCREENHEIGHT = REALTILESIZE * MAXSCREENROW;
    private final int speed = 4;
    private Thread gameThread;
    private int FPS = 60;
    private long drawDelay  = SECONDTONANO/FPS;
    private long lastFrame = System.nanoTime();
    private long currentTime;
    private double deltaTime = 0;
    private int pX = 100;
    private int pY = 100;
    private double timer;
    public double fpsCount = 0;
    public KeyHandler keyH = new KeyHandler();

    public GamePanel(){
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
        if(keyH.rightP && currentDir != Direction.Left){
            currentDir = Direction.Right;
        } else if (keyH.leftP && currentDir != Direction.Right) {
            currentDir = Direction.Left;
        } else if (keyH.upP && currentDir != Direction.Downward) {
            currentDir = Direction.Upward;
        } else if (keyH.downP && currentDir != Direction.Upward) {
            currentDir = Direction.Downward;
        }

        switch (currentDir){
            case Left:
                pX -= 4;
                break;
            case Right:
                pX += 4;
                break;
            case Upward:
                pY -= 4;
                break;
            case Downward:
                pY += 4;
                break;
        }
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
        g2.setColor(Color.white);
        g2.fillRect(pX,pY, REALTILESIZE, REALTILESIZE);
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


    public int getpX() {
        return pX;
    }

    public void setpX(int pX) {
        this.pX = pX;
    }

    public int getpY() {
        return pY;
    }

    public void setpY(int pY) {
        this.pY = pY;
    }

    public int getFps() {
        return FPS;
    }

    public void setFps(int fps) {
        this.FPS = fps;
        drawDelay = 1000000000/fps;
    }

    public int getSpeed() {
        return speed;
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

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
    private final int UPSCALE = 3;

    private final int REALTILESIZE = TILESIZE*UPSCALE; //40x40
    private final int MAXSCREENCOL = 16;
    private final int MAXSCREENROW = 12;
    private final int SCREENWIDTH = REALTILESIZE * MAXSCREENCOL;
    private final int SCREENHEIGHT = REALTILESIZE * MAXSCREENROW;
    private final int speed = 4;
    private Thread gameThread;
    private int FPS = 60;
    private final long drawDelay  = 1000000000/FPS;
    private long nextFrame = System.nanoTime() + drawDelay;
    private int pX = 100;
    private int pY = 100;
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
        if(keyH.rightP){
            pX += 4;
        } else if (keyH.leftP) {
            pX -= 4;
        } else if (keyH.upP) {
            pY += 4;
        } else if (keyH.downP) {
            pY -= 4;
        }
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
        g2.setColor(Color.white);
        g2.fillRoundRect(pX,pY, 30, 100, 10, 10);
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
            nextFrame = System.nanoTime()+drawDelay;
            update();
            repaint();
            long remainderTime = nextFrame -System.nanoTime();
            if(remainderTime < 0){
                remainderTime = 0;
            }
            try {
                Thread.sleep(remainderTime/1000000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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

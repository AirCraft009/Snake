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
    final int TILESIZE = 16;
    /**
     * while the graphic tiles are 16x16
     * they are upscaled because 16x16 pixels
     * on a 1080p screen is too small
     */
    final int UPSCALE = 3;

    final int REALTILESIZE = TILESIZE*UPSCALE; //40x40
    final int MAXSCREENCOL = 16;
    final int MAXSCREENROW = 12;
    final int SCREENWIDTH = REALTILESIZE * MAXSCREENCOL;
    final int SCREENHEIGHT = REALTILESIZE * MAXSCREENROW;
    Thread gameThread;

    public GamePanel(){
        setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    public void StartGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){

    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
        g2.setColor(Color.white);
        g2.fillRoundRect(100, 100, 30, 100, 10, 10);
        g2.dispose();
    }

    @Override
    public void run() {
        int x = 0;
        int y = 0;
        /**
         * In this loop two things will happen first of all there is an update-cycle
         * meaning, that it updates where everything is for example the player.
         *
         * 2. The drawing stage in this part of the loop everything thing that is updated is deleted and drawn again
         */
        while (gameThread != null){
            update();
            repaint();
            y++;
        }
    }
}

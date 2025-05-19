package main.java.main;
import main.java.entity.*;
import main.java.tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
    private final int MAXSCREENCOL = 18;
    private final int MAXSCREENROW = 16;
    private final int SCREENWIDTH = REALTILESIZE * MAXSCREENCOL;
    private final int SCREENHEIGHT = REALTILESIZE * MAXSCREENROW;

    //TIME SETTINGS
    private int FPS = 60;
    private long drawDelay;
    private long lastFrame = System.nanoTime();
    private long currentTime;
    private double deltaSpeed;
    private double deltaTime = 0;
    public double fpsCount = 0;
    private double timer;


    //POSITIONAL SETTINGS
    public int currTick = 0;
    public int baseSpeed = REALTILESIZE;
    public Mode players = Mode.Single;

    public Difficulty diff;
    public GameStates state = GameStates.MENUE;
    public KeyHandler keyH = new KeyHandler(this);
    private Thread gameThread;
    public TileManager tileManager = new TileManager(this, "basic");
    private FoodPlacer foodPlacer;
    private boolean saved = false;
    private Snake[] snakes;


    public GamePanel(Difficulty d, Mode mode) throws IOException {
        diff = d;
        snakes = new Snake[(mode == Mode.Single)? 1: 2];
        foodPlacer = new FoodPlacer(this, (mode == Mode.Single)? 1 : 2, tileManager);
        for (int i = 0; i < snakes.length; i++) {
            snakes[i] = new Snake(this, keyH, 144, 192+i*48, 3, foodPlacer);
        }
        setFocusable(true);
        setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyH);
        deltaSpeed = (double) baseSpeed/FPS;
    }

    public void StartNewGame(String mapName, Difficulty diff, boolean superfruits, Mode mode){
        
    }

    public void setToPrevSave(int saveNum){

    }

    public void StartGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void pauseGame(){
        state = GameStates.PAUSED;
    }

    public void update(){
        if (state == GameStates.DEATH){
            saveCurrentProgress();
            return;
        }
        if(state == GameStates.RUNNING) {
            for (Snake snake : snakes) {
                snake.update(currTick);
            }
        }
    }

    public String toString(){
        LocalDateTime d = LocalDateTime.now();;
        StringBuilder builder = new StringBuilder("Date: ").append(d.format(DateTimeFormatter.ofPattern("dd;MM;yy;mm;ss")));
        builder.append("\nmapName: ").append(tileManager.mapName);
        builder.append("\nMode: ").append(players);
        builder.append("\nDifficulty: ").append(diff);
        builder.append("\nSuperfruits_enabled: ").append(false);
        for (int i = 0; i < snakes.length; i++) {
            Snake s1 = snakes[i];
            builder.append("\nSnake-Save").append(i);
            builder.append("\nfruitseaten").append(i).append(": ").append(s1.fruitsEaten).append("\n");
            builder.append(s1.toString());
        }
        return builder.toString();
    }

    public void saveCurrentProgress(){
        if(saved){
            return;
        }
        saved = true;
        String userHome = System.getProperty("user.dir");
        String finalPath = userHome + "/src/main/resources/saves/save1.txt";
        System.out.println(finalPath);
        try {
            File myObj = new File(finalPath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists." + myObj.getName());
            }
            FileWriter myWriter = new FileWriter(finalPath);
            myWriter.write(toString());
            myWriter.close();
            System.out.println("Successfully wrote save to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics gr){
        super.paintComponent(gr);
        Graphics2D g2 = (Graphics2D)gr;
        if(state == GameStates.MENUE) {
            drawMenu(g2);
            g2.dispose();
            return;
        }
        tileManager.blit(g2);
        if(state == GameStates.DEATH){
            for (Snake snake : snakes){
                snake.freezeBlit(g2);
            }
            g2.dispose();
            return;
        }
        if(state == GameStates.RUNNING) {
            for (Snake snake : snakes) {
                snake.blit(g2);
            }
            g2.dispose();
        }
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
                deltaTime = 0;
                fpsCount ++;
            }
            if (timer >= SECONDTONANO){
                //System.out.println(fpsCount);
                fpsCount = 0;
                timer = 0;
            }

        }
    }


    private void drawMenu(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("Snake Game", 100, 100);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString("Press ENTER to Start", 100, 150);
    }



    public int getFps() {
        return FPS;
    }

    public void setFps(int fps) {
        this.FPS = fps;
        drawDelay = SECONDTONANO/FPS;
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

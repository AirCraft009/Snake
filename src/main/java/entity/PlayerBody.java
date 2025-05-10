package main.java.entity;

import main.java.main.Direction;
import main.java.main.GamePanel;
import main.java.main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerBody extends Entity{
    GamePanel gp;
    public Direction internDir = Direction.None;
    private Direction prevDir = Direction.None;
    private HashMap <String, BufferedImage> ImageMap = new HashMap<>();
    public PlayerBody(GamePanel gp, int newX, int newY, EntityType type) throws IOException {
        this.type = type;
        this.gp = gp;
        this.x = newX;
        this.y = newY;
        getImages();
    }

    public void update(int nextX, int nextY, Direction newDir){
        x = nextX;
        y = nextY;
        prevDir = newDir;
    }

    private BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entities/snake/" + name + ".png")));
    }

    public void getImages() throws IOException {
        ImageMap.putAll(Map.of(
            "vert", getImage("snake-body"),
            "hor", getImage("snake-body-side"),
            "up-left", getImage("snake-body-up-left"),
            "up-right", getImage("snake-body-up-right"),
            "down-left", getImage("snake-body-down-left"),
            "down-right", getImage("snake-body-down-right")
        ));
    }

    public void blit(Graphics g2){
        g2.setColor(Color.WHITE);
        String key = switch (internDir) {
            case Upward    -> prevDir == Direction.Right  ? "down-right" :
                             prevDir == Direction.Left   ? "down-left"  :
                                                                       "vert";
            case Downward  -> prevDir == Direction.Right  ? "up-right"   :
                             prevDir == Direction.Left   ? "up-left"    :
                                                                       "vert";
            case None, Right -> prevDir == Direction.Upward ? "up-left"   :
                                prevDir == Direction.Downward ? "down-left":
                                                                       "hor";
            case Left -> prevDir == Direction.Upward ? "up-right"  :
                          prevDir == Direction.Downward ? "down-right":
                                                                       "hor";
        };
        BufferedImage image = ImageMap.get(key);
        g2.drawImage(image, x,y, gp.REALTILESIZE, gp.REALTILESIZE, null);
        internDir = prevDir;

    }


}

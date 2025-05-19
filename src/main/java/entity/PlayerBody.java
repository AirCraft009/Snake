package main.java.entity;

import main.java.main.Direction;
import main.java.main.GamePanel;

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
    public Direction prevDir = Direction.None;
    private HashMap <String, BufferedImage> ImageMap = new HashMap<>();
    private Direction rollBackDir1 = Direction.None;
    private Direction rollBackDir2 = Direction.None;

    public PlayerBody(GamePanel gp, int newX, int newY, EntityType type) throws IOException {
        this.type = type;
        this.gp = gp;
        this.x = newX;
        this.y = newY;
        getImages();
    }

    public void update(int nextX, int nextY, Direction newDir){
        prevX = x;
        prevY = y;
        x = nextX;
        y = nextY;
        prevDir = newDir;
    }

    private BufferedImage getImage(String name) throws IOException {
        return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/textures/entities/snake/" + name + ".png")));
    }

    public void getImages() throws IOException {
        if(type == EntityType.PlayerBody) {
            ImageMap.putAll(Map.of(
                    "vert", getImage("snake-body"),
                    "hor", getImage("snake-body-side"),
                    "up-left", getImage("snake-body-up-left"),
                    "up-right", getImage("snake-body-up-right"),
                    "down-left", getImage("snake-body-down-left"),
                    "down-right", getImage("snake-body-down-right")
            ));
        } else if (type == EntityType.PlayerTail) {
            ImageMap.putAll(Map.of(
                    "up", getImage("snake-tail-up"),
                    "down", getImage("snake-tail-down"),
                    "right", getImage("snake-tail-right"),
                    "left", getImage("snake-tail-left"),
                    "up-left", getImage("snake-tail-up-left"),
                    "up-right", getImage("snake-tail-up-right"),
                    "down-left", getImage("snake-tail-down-left"),
                    "down-right", getImage("snake-tail-down-right")
            ));
        }
    }

    public String getNextSprite(){
        if(type == EntityType.PlayerBody){
            return getNextDirectionSprite();
        } else if (type == EntityType.PlayerTail) {
            return getNextDirectionSpriteTail();
        }
        return " ";
    }

    public String getNextDirectionSprite(){
        return switch (internDir) {
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
    }

    public String getNextDirectionSpriteTail(){
        return switch (internDir) {
            case Upward    -> prevDir == Direction.Right  ? "down-right" :
                    prevDir == Direction.Left   ? "down-left"  :
                            "up";
            case Downward  -> prevDir == Direction.Right  ? "up-right"   :
                    prevDir == Direction.Left   ? "up-left"    :
                            "down";
            case None, Right -> prevDir == Direction.Upward ? "up-left"   :
                    prevDir == Direction.Downward ? "down-left":
                            "right";
            case Left -> prevDir == Direction.Upward ? "up-right"  :
                    prevDir == Direction.Downward ? "down-right":
                            "left";
        };
    }

    public void blit(Graphics g2){
        g2.setColor(Color.WHITE);
        BufferedImage image = ImageMap.get(getNextSprite());
        g2.drawImage(image, x,y, gp.REALTILESIZE, gp.REALTILESIZE, null);
        I1 = image;
        rollBackDir1 = internDir;
        internDir = prevDir;

    }

    public void frezeeblit(Graphics2D g2){
        internDir = rollBackDir1;
        BufferedImage image = ImageMap.get(getNextSprite());
        g2.drawImage(image, x,y, gp.REALTILESIZE, gp.REALTILESIZE, null);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder("pos: ").append(x/48).append(", ").append(y/48).append("\n");
        builder.append("Dir: ").append(internDir);
        builder.append("\nprevDir: ").append(prevDir);
        builder.append("\ntype: ").append(type);
        return builder.toString();
    }
}

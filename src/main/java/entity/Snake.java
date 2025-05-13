package main.java.entity;

import main.java.main.Direction;
import main.java.main.GamePanel;
import main.java.main.GameStates;
import main.java.main.KeyHandler;
import main.java.sounds.SoundManager;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Snake {
    GamePanel gp;
    KeyHandler KeyH;
    ArrayList <PlayerBody> body = new ArrayList<PlayerBody>();
    public final PlayerHead head;
    public boolean first;
    public ColissionManager cm;
    Direction prevSnakeheadDir = Direction.None;
    SoundManager sM;

    public Snake(GamePanel gp, KeyHandler KeyH, int x, int y, int len) throws IOException {
        sM = new SoundManager();
        this.gp = gp;
        this.KeyH = KeyH;
        head = new PlayerHead(this.gp, KeyH);
        head.setDefault(x, y);
        for (int i = 1; i < len; i++) {
            body.add(new PlayerBody(gp, x-48*i, y, EntityType.PlayerBody));
        }
        body.add(new PlayerBody(gp, x-48*len, y, EntityType.PlayerTail));
        cm = new ColissionManager(gp, gp.tileManager, this);
        sM.playSoundLoop("/audio/music/bassie.wav");

    }

    public void update(int tick) {
        int prevX = head.x;
        int prevY = head.y;
        prevSnakeheadDir = head.currentDir;
        int bodyX;
        int bodyY;
        head.update(tick);
        if(head.currentDir != prevSnakeheadDir)
            sM.playMoveSound();
        if(cm.checkWallColission() ||  cm.checkOwnColission()) {
            sM.playHitsound();
            head.rollback(prevX, prevY, prevSnakeheadDir);
            gp.state = GameStates.DEATH;
            return;
        }
        if(head.currentDir != Direction.None) {
            for(int i = 0; i < body.size(); i++) {
                PlayerBody p1 = body.get(i);
                bodyX = p1.x;
                bodyY = p1.y;
                if(i == 0)
                    p1.update(prevX, prevY, head.currentDir);
                else {
                    p1.update(prevX, prevY, body.get(i-1).internDir);
                }
                prevX = bodyX;
                prevY = bodyY;
            }
        }
    }

    public void eatFruit(int extralen){
        for (int i = 0; i < extralen; i++) {
            try {
                body.getLast().type = EntityType.PlayerBody;
                int xOffset = 0;
                int yOffset = 0;
                switch (body.getLast().internDir){
                    case Upward -> yOffset = gp.REALTILESIZE;
                    case Downward -> yOffset = -gp.REALTILESIZE;
                    case Right, None -> xOffset = gp.REALTILESIZE;
                    case Left -> xOffset = -gp.REALTILESIZE;
                }
                body.addLast(new PlayerBody(gp, body.getLast().x+xOffset , body.getLast().y+yOffset, EntityType.PlayerTail));
                body.getLast().internDir = body.get(body.size()-2).internDir;
                body.getLast().prevDir = body.get(body.size()-2).internDir;
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public void blit(Graphics2D g2){
        head.blit(g2);
        for (PlayerBody p1 : body) {
            p1.blit(g2);
        }
    }

    public void freezeBlit(Graphics2D g2){
        head.blit(g2);
        for (PlayerBody p1 : body) {
            p1.frezeeblit(g2);
        }
    }
}

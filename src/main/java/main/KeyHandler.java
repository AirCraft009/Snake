package main.java.main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean rightP, leftP, upP, downP;
    GamePanel gp;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        upP  = false;
        downP = false;
        leftP = false;
        rightP = false;
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upP = true;
        } else if (code == KeyEvent.VK_S) {
            downP = true;
        } else if (code == KeyEvent.VK_A) {
            leftP = true;
        } else if (code == KeyEvent.VK_D) {
            rightP = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
            upP = false;
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
            downP = false;
        } else if (code == KeyEvent.VK_LEFT || code == KeyEvent.VK_A) {
            leftP = false;
        } else if (code == KeyEvent.VK_RIGHT || code == KeyEvent.VK_D) {
            rightP = false;
        } else if (code == KeyEvent.VK_ENTER&&gp.state == GameStates.MENUE) {
            gp.state = GameStates.RUNNING;
        }
    }
}

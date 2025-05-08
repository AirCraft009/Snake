import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    boolean rightP, leftP, upP, downP = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                upP = true;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                downP = true;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftP = true;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightP = true;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code){
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                upP = true;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                downP = true;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                leftP = true;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                rightP = true;
            default:
                break;
        }
    }
}

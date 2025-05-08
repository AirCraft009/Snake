package main;
import javax.swing.*;
import java.awt.*;

public class WINDOW extends JFrame {
    //use this constructor when the game isn't supposed to be in fullscreen
    //probably the main Constructor
    public WINDOW(int height, int width) {
        basicInit();
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    //this is the fullscreen constructor
    public WINDOW() {
        basicInit();

        GraphicsDevice gd = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice();

        if (gd.isFullScreenSupported()){
            gd.setFullScreenWindow(this);
        }
        //only if there's no support for fullscreen
        else {
            //maximize Window
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            //sets the minimum size to the max it can be so
            //it will never be smaller than maximized
            setMinimumSize(new java.awt.Dimension(MAXIMIZED_VERT, MAXIMIZED_HORIZ));
        }
    }

    private void basicInit(){
        setTitle("Snake");
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}

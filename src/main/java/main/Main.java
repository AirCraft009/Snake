package main.java.main;

import main.java.main.Difficulty;

import java.io.IOException;

public class Main {
    GameStates CurrentState = GameStates.MENUE;
    Difficulty difficulty;

    public static void main(String[] args) throws IOException {
        GamePanel MainPanel = new GamePanel(Difficulty.MID, Mode.Double);
        WINDOW GameWindow = new WINDOW(MainPanel.getSCREENHEIGHT(), MainPanel.getSCREENWIDTH());
        GameWindow.add(MainPanel);
        MainPanel.setFps(7);
        MainPanel.StartGame();
        MainPanel.requestFocusInWindow();

    }
}

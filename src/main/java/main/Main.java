package main.java.main;

import main.java.main.Difficulty;

import java.io.IOException;

public class Main {
    GameStates CurrentState = GameStates.MENUE;
    Difficulty difficulty;

    public static void main(String[] args) throws IOException {
        GamePanel MainPanel = new GamePanel(Difficulty.MID, Mode.Single);
        WINDOW GameWindow = new WINDOW(MainPanel.getSCREENHEIGHT(), MainPanel.getSCREENWIDTH());
        GameWindow.add(MainPanel);
        MainPanel.setFps(5);
        MainPanel.StartGame();
        MainPanel.requestFocusInWindow();

    }
}

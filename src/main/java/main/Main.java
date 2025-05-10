package main.java.main;

import main.java.main.*;
public class Main {
    GameStates CurrentState = GameStates.MENUE;
    Difficulty difficulty;

    public static void main(String[] args) {
        GamePanel MainPanel = new GamePanel();
        WINDOW GameWindow = new WINDOW(MainPanel.getSCREENHEIGHT(), MainPanel.getSCREENWIDTH());
        GameWindow.add(MainPanel);
        MainPanel.setFps(20);
        MainPanel.StartGame();
        MainPanel.requestFocusInWindow();

    }
}

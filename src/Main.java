public class Main {
    GameStates CurrentState = GameStates.MENUE;
    Difficulty difficulty;

    public static void main(String[] args) {
        GamePanel MainPanel = new GamePanel();
        WINDOW GameWindow = new WINDOW(MainPanel.getSCREENHEIGHT(), MainPanel.getSCREENWIDTH());
        GameWindow.add(MainPanel);
        MainPanel.StartGame();

    }
}

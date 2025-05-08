public class Main {
    GameStates CurrentState = GameStates.MENUE;
    Difficulty difficulty;

    public static void main(String[] args) {
        GamePanel MainPanel = new GamePanel();
        WINDOW GameWindow = new WINDOW(MainPanel.SCREENHEIGHT, MainPanel.SCREENWIDTH);
        GameWindow.add(MainPanel);
        MainPanel.StartGame();

    }
}

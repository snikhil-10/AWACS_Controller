import javax.swing.JFrame;

public class GameFrame extends JFrame {

    private GamePanel gamePanel;

    public GameFrame() {
        setTitle("AWACS Controller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);

        pack();//fits in window
        setLocationRelativeTo(null);//center
        setVisible(true);
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

    private Timer timer;
    private SoundManager soundManager;
    private final int PANEL_WIDTH = 1000;
    private final int PANEL_HEIGHT = 700;
    private final int radarCenterX = 500;
    private final int radarCenterY = 320;
    private final int radius = 250;
    private GameWorld world;
    private GameController controller;
    private Image friendlySprite;
    private Image enemySprite;
    private Image baseSprite;
    private Image missileSprite;
   
  
    private String playerCallsign;

    private double sweepAngle = 0;
   
    

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);
        soundManager = new SoundManager();
        friendlySprite = new ImageIcon("sprites/friendly.png").getImage();
        enemySprite = new ImageIcon("sprites/enemy.png").getImage();
        baseSprite = new ImageIcon("sprites/base.png").getImage();
        missileSprite = new ImageIcon("sprites/missile.png").getImage();
        playerCallsign = JOptionPane.showInputDialog(
        	null, "Enter callsign: ", "Air Staff Management Interface Login", JOptionPane.QUESTION_MESSAGE);
        if(playerCallsign == null || playerCallsign.trim().equals("")) {
        		playerCallsign = "HOMEPLATE";
        }
        
        playerCallsign = playerCallsign.toUpperCase();
        
        addMouseListener(this);

        world = new GameWorld(radarCenterX, radarCenterY, radius);
        controller = new GameController(world);
        world.addLog("Welcome, " + playerCallsign + ".");
        soundManager.playCommandConfirm();
        timer = new Timer(16, this);
        timer.start();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (!world.isGameOver() && !world.isVictory()) {
            updateGame();
        }

        repaint();
    }

    private void updateGame() {
        sweepAngle += 0.03;

        if (sweepAngle > Math.PI * 2) {
            sweepAngle = 0;
            soundManager.playRadarPing();
        }

        world.update();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        drawBackground(g2);
        drawLeftPanel(g2);
        drawRadar(g2);
        drawUnits(g2);
        drawBottomLog(g2);
        drawHUD(g2);

        if (world.isGameOver()) {
            drawGameOver(g2);
        }

        if (world.isVictory()) {
            drawVictory(g2);
        }
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
    }

    private void drawLeftPanel(Graphics2D g2) {
        g2.setColor(new Color(15, 20, 25));
        g2.fillRect(0, 0, 220, PANEL_HEIGHT);

        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2.drawString("BLUE FORCE", 15, 30);

        int y = 70;

        for (FriendlyUnit unit : world.getFriendlyUnits()) {

            g2.setColor(Color.CYAN);
            g2.setFont(new Font("Monospaced", Font.BOLD, 13));
            g2.drawString(unit.getCallsign(), 15, y);

            g2.setColor(Color.GREEN);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g2.drawString("HP: " + unit.getHealth(), 15, y + 18);

            g2.drawString("STATUS: READY", 95, y + 18);
            y += 70;
        }

        g2.setColor(Color.RED);
        g2.setFont(new Font("Monospaced", Font.BOLD, 18));
        g2.drawString("RED FORCE", 15, 330);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("CONTACTS: " + world.getEnemyUnits().size(), 15, 355);
    }

    private void drawRadar(Graphics2D g2) {
        g2.setColor(new Color(0, 90, 100));
        g2.drawOval(radarCenterX - radius,
                radarCenterY - radius,
                 radius * 2,
                 radius * 2
        );

        for (int r = 50; r <= radius; r += 50) {
            g2.setColor(new Color(0, 60, 70));
            g2.drawOval(radarCenterX - r, radarCenterY - r, r * 2, r * 2);
        }

        g2.setColor(new Color(0, 60, 70));
        g2.drawLine(radarCenterX - radius, radarCenterY, radarCenterX + radius, radarCenterY);
        g2.drawLine(radarCenterX, radarCenterY - radius, radarCenterX, radarCenterY + radius);

        int sweepX = radarCenterX + (int) (Math.cos(sweepAngle) * radius);
        int sweepY = radarCenterY + (int) (Math.sin(sweepAngle) * radius);

        g2.setColor(new Color(0, 255, 255));
        g2.drawLine(radarCenterX, radarCenterY, sweepX, sweepY);
        int baseX = (int) world.getBase().getX();
        int baseY = (int) world.getBase().getY();

        g2.drawImage(baseSprite, baseX - 14, baseY - 14, 28, 28, null);

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("BASE", baseX + 15, baseY - 10);
    }

    private void drawUnits(Graphics2D g2) {
        for (FriendlyUnit friendly : world.getFriendlyUnits()) {
            g2.drawImage(
                    friendlySprite,
                    (int) friendly.getX() - 12,
                    (int) friendly.getY() - 12,
                    24,
                    24,
                    null
            );

            g2.setColor(Color.CYAN);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g2.drawString(
                    friendly.getCallsign(),
                    (int) friendly.getX() + 12,
                    (int) friendly.getY()
            );
        }
        for (EnemyUnit enemy : world.getEnemyUnits()) {
            g2.drawImage(
                    enemySprite,
                    (int) enemy.getX() - 10,
                    (int) enemy.getY() - 10,
                    20,
                    20,
                    null
            );
            g2.setColor(Color.RED);
            g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
            g2.drawString(
                    enemy.getName(),
                    (int) enemy.getX() + 10,
                    (int) enemy.getY()
            );
        }
        for (Missile missile : world.getMissiles()) {
            g2.drawImage(
                    missileSprite,
                    (int) missile.getX() - 8,
                    (int) missile.getY() - 8,
                    16,
                    16,
                    null
            );
        }
    }

    private void drawBottomLog(Graphics2D g2) {
        int logY = 600;

        g2.setColor(new Color(15, 20, 25));
        g2.fillRect(0, logY, PANEL_WIDTH, 100);

        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Monospaced", Font.BOLD, 13));
        g2.drawString("TACTICAL DATA LINK LOG", 15, logY + 20);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));

        int y = logY + 40;

        for (String msg : world.getCommandLog()) {
        	if (msg.contains("missed") || msg.contains("MISSED")) {
        	    g2.setColor(Color.RED);
        	} else if (msg.startsWith("ALERT")) {
        	    g2.setColor(Color.ORANGE);
        	} else if (msg.startsWith("SYS")) {
        	    g2.setColor(Color.GREEN);
        	} else {
        	    g2.setColor(Color.CYAN);
        	}

            g2.drawString(msg, 15, y);
            y += 14;
        }
    }

    private void drawHUD(Graphics2D g2) {
        g2.setColor(Color.CYAN);
        g2.setFont(new Font("Monospaced", Font.BOLD, 14));

        g2.drawString("CALLSIGN: " + playerCallsign, 760, 30);
        g2.drawString("KILLS: " + world.getKills(), 760, 50);
        g2.drawString("BASE HP: " + world.getBaseHealth(), 760, 70);

        g2.setColor(Color.ORANGE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 28));
        g2.drawString("WAVE " + world.getWave(), 760, 155);
        
		g2.setColor(Color.GREEN);
		g2.setFont(new Font("Monospaced", Font.BOLD, 28));
		g2.drawString("SCORE " + world.getScore(), 820, 455);
    }

    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        g2.setColor(Color.RED);
        g2.setFont(new Font("Monospaced", Font.BOLD, 48));
        g2.drawString("GAME OVER", 350, 320);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.drawString("Final Score: " + world.getScore(), 400, 360);
    }
    private void drawVictory(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Monospaced", Font.BOLD, 48));
        g2.drawString("MISSION SUCCESS", 270, 320);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        g2.drawString("Final Score: " + world.getScore(), 400, 360);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (world.isGameOver() || world.isVictory()) {
            return;
        }
        controller.handleMouseClick(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

 
}
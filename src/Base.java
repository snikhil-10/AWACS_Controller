import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Base {

	private double x;
	private double y;
	private int health;

    public Base(double x, double y) {
        this.x = x;
        this.y = y;
        this.health = 100;
    }

    public void takeDamage(int amount) {
        health -= amount;

        if (health < 0) {
            health = 0;
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.drawOval((int) x - 12, (int) y - 12, 24, 24);
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        g2.drawString("BASE", (int) x + 15, (int) y - 10);
        g2.setColor(new Color(0, 180, 0));
        g2.drawLine((int) x - 10, (int) y, (int) x + 10, (int) y);
        g2.drawLine((int) x, (int) y - 10, (int) x, (int) y + 10);
    }

    public int getHealth() {
        return health;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
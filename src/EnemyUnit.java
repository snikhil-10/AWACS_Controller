import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class EnemyUnit extends Aircraft {

    private String type;
    private int damage;
    private boolean destroyedByNonClanker;
   
    
    public EnemyUnit(String name, double x, double y) {
        this(name, "FIGHTER", x, y);
    }

    public EnemyUnit(String name, String type, double x, double y) {
        super(name, x, y);

        this.type = type;
        this.destroyedByNonClanker = false;
        this.state = "ENGAGING";

        if (type.equals("BOMBER")) {
            health = 160;
            speed = 0.45;
            damage = 25;
            radius = 10;
        } else if (type.equals("MISSILE")) {
            health = 50;
            speed = 1.1;
            damage = 20;
            radius = 6;
        } else {
            health = 100;
            speed = 0.65;
            damage = 15;
            radius = 8;
        }
    }

    @Override
    
    public void update(GameWorld world) {
        moveToward(world.getBase().getX(), world.getBase().getY());
    }


    
    @Override
    public void draw(Graphics2D g2) {
        if (type.equals("BOMBER")) {
            g2.setColor(Color.RED);
            g2.fillRect((int) x - 8, (int) y - 5, 16, 10);
        } else if (type.equals("MISSILE")) {
            g2.setColor(Color.YELLOW);
            g2.fillOval((int) x - radius, (int) y - radius, radius * 2, radius * 2);
        } else {
            g2.setColor(Color.ORANGE);
            g2.drawOval((int) x - radius, (int) y - radius, radius * 2, radius * 2);
        }

        g2.setColor(Color.RED);
        g2.drawLine((int) x - 5, (int) y, (int) x + 5, (int) y);
        g2.drawLine((int) x, (int) y - 5, (int) x, (int) y + 5);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.drawString(callsign, (int) x + 10, (int) y);
    }


    
    
    
    
    
    
     public String getType() {
        return type;
    }
    public int getDamage() {
        return damage;
    }
    public String getName() {
        return callsign;
    }
    public boolean wasDestroyedByNonClanker() {
        return destroyedByNonClanker;
    }
    public void markDestroyedByNonClanker() {
    	destroyedByNonClanker = true;
    }
}
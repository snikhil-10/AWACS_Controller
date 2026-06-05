import java.awt.*;

public class FriendlyUnit extends Aircraft {

    public FriendlyUnit(String callsign, double x, double y) {
        super(callsign, x, y);
        speed = 1.8;
        radius = 8;
    }

    @Override
    public void update(GameWorld world) {
        setState("IDLE");
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.CYAN);

        int[] xPoints = {(int) x, (int) x - 8, (int) x + 8};
        int[] yPoints = {(int) y - 10, (int) y + 8, (int) y + 8};//le triangle snip snip snip

        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
        g2.drawString(callsign, (int) x + 10, (int) y);
    }

}
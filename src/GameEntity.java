import java.awt.Graphics2D;

public abstract class GameEntity {

    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double speed;
    protected int radius;
    protected boolean alive;

    public GameEntity(double x, double y) {
        this.x = x;
        this.y = y;
        this.vx = 0;
        this.vy = 0;
        this.speed = 1.0;
        
        this.radius = 8;
        this.alive = true;
    }

    public abstract void update(GameWorld world);

    public abstract void draw(Graphics2D g2);

    public double distanceTo(GameEntity other) {
        double dx = other.x - x;
        double dy = other.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double distanceTo(double otherX, double otherY) {
        double dx = otherX - x;
        double dy = otherY - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean containsPoint(int px, int py) {
        double dx = px - x;
        double dy = py - y;
        double clickRadius = Math.random() * 12.0 + 6.0; //good luck clicking haha
        return Math.sqrt(dx * dx + dy * dy) <= radius + clickRadius;
    }

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        alive = false;
    }

    public void moveToward(double targetX, double targetY) {//for enemy movement
        double dx = targetX - x;
        double dy = targetY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist > 0) {
            vx = (dx / dist) * speed;
            vy = (dy / dist) * speed;

            x += vx;
            y += vy;
        }
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public int getRadius() {
        return radius;
    }
    public double getSpeed() {
    	return speed;
    }
    public void setSpeed(double speed) {
    	this.speed = speed;
    }
}
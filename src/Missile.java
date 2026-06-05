import java.awt.*;

public class Missile extends GameEntity {

    private EnemyUnit target;

    public Missile(double x, double y, EnemyUnit target) {
        super(x, y);
        this.target = target;
        speed = 4.0;
        radius = 5;
    }

    @Override
    public void update(GameWorld world) {
        if (target == null || !target.isAlive()) {
            destroy();
            return;
        }
        moveToward(target.getX(), target.getY());
        if (distanceTo(target) <= radius + target.getRadius()) {
            double hitChance = 0.75;
            if (Math.random() < hitChance) {
                target.markDestroyedByNonClanker();
                target.destroy();
                world.playExplosion();
                world.addLog("SYS: Missile hit, splash 1 " + target.getName() + ".");
            } else {
                world.addLog("SYS: Interceptor missed, re-engage! " + target.getName() + ".");
                world.playMiss();
            }

            destroy();
        }
    }
    
    
    
    public EnemyUnit getTarget() {
        return target;
    }

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
}
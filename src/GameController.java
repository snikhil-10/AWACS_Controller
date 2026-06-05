public class GameController {

    private GameWorld world;

    public GameController(GameWorld world) {
        this.world = world;
    }

    public boolean handleMouseClick(int x, int y) {
        if (world.isGameOver() || world.isVictory()) {
            return false;
        }

        for (EnemyUnit enemy : world.getEnemyUnits()) {
            if (enemy.containsPoint(x, y) && enemy.isAlive()) {
            	world.launchMissileAt(enemy);
            	return true;
            }
        }

        return false;
    }
}
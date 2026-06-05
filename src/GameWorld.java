import java.util.ArrayList;

public class GameWorld {

	private ArrayList<FriendlyUnit> friendlyUnits;
	private ArrayList<EnemyUnit> enemyUnits;
	private ArrayList<String> commandLog;
	private ArrayList<Missile> missiles;
	private Base base;
	private WaveManager waveManager;
	private ScoreManager scoreManager;
	private SoundManager sound;
	
    private int radarCenterX;
    private int radarCenterY;
    private int radarRadius;
    
    private boolean gameOver;
    private boolean victory;	


    public GameWorld(int radarCenterX, int radarCenterY, int radarRadius) {
        this.radarCenterX = radarCenterX;
        this.radarCenterY = radarCenterY;
        this.radarRadius = radarRadius;

        friendlyUnits = new ArrayList<FriendlyUnit>();
        enemyUnits = new ArrayList<EnemyUnit>();
        commandLog = new ArrayList<String>();
        missiles = new ArrayList<Missile>();
        base = new Base(radarCenterX, radarCenterY);
        waveManager = new WaveManager();
        scoreManager = new ScoreManager();
        sound = new SoundManager();


        gameOver = false;
        victory = false;

        createStartingUnits();

        addLog("Magic 01 (E-3 AWACS) online.");
    }

    private void createStartingUnits() {
        friendlyUnits.add(new FriendlyUnit("Patriot PAC-3 Battery", 450, 360));
        friendlyUnits.add(new FriendlyUnit("IRIS-T Battery", 630, 220));
        friendlyUnits.add(new FriendlyUnit("Guy holding a missile", 500, 490));
    }

    public void update() {
        if (gameOver || victory) {
            return;
        }
        waveManager.update(this);

        for (FriendlyUnit friendly : friendlyUnits) {
            friendly.update(this);
        }

        for (EnemyUnit enemy : enemyUnits) {
            enemy.update(this);
        }

        for (Missile missile : missiles) {
            missile.update(this);
        }

        removeDestroyedMissiles();
        checkEnemiesReachingBase();
        removeDestroyedEnemies();
    }

    private void checkEnemiesReachingBase() {
        for (EnemyUnit enemy : enemyUnits) {
        	if (enemy.isAlive() && enemy.distanceTo(base.getX(), base.getY()) < 20) {
        	    enemy.destroy();
        	    base.takeDamage(enemy.getDamage());
        	    sound.playAlert();
        	    addLog("ALERT: Enemy reached protected airspace. Air assets unavailable, get them or were done!");
        	    

        	    if (base.isDestroyed()) {
        	    	sound.playLoss();
        	        gameOver = true;
        	        addLog("SYS: BASE DESTROYED. WE HAVE TO FALL BACK. GAME OVER");
        	    }
        	}
        }
    }

    private void removeDestroyedEnemies() {
        for (int i = enemyUnits.size() - 1; i >= 0; i--) {
            EnemyUnit enemy = enemyUnits.get(i);

            if (!enemy.isAlive()) {
                if (enemy.wasDestroyedByNonClanker()) {
                    scoreManager.addKill();
                    addLog("SYS: Splash 1. +100");
                }
                enemyUnits.remove(i);
            }
        }
    }
    
    private void removeDestroyedMissiles() {
        for (int i = missiles.size() - 1; i >= 0; i--) {
            if (!missiles.get(i).isAlive()) {
                missiles.remove(i);
            }
        }
    }

    public void launchMissileAt(EnemyUnit enemy) {
        if (enemy == null || !enemy.isAlive()) {
            return;
        }
        
        for (Missile missile : missiles) {
            if (missile.isAlive() && missile.getTarget() == enemy) {
                return;
            }

        }

        if (friendlyUnits.size() == 0) {
            return;
        }

        int randomIndex = (int) (Math.random() * friendlyUnits.size());

        FriendlyUnit launcher = friendlyUnits.get(randomIndex);
        Missile missile = new Missile(launcher.getX(), launcher.getY(), enemy);
        missiles.add(missile);
        sound.playLaunch();
        addLog("SYS: " + launcher.getCallsign() + " Missile launched at " + enemy.getName() + ".");
    }

    public void addLog(String message) {
        commandLog.add(message);

        if (commandLog.size() > 6) {
            commandLog.remove(0);
        }
    }

    
    
    
    
    
    
    public ArrayList<FriendlyUnit> getFriendlyUnits() {
        return friendlyUnits;
    }
    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
    public Base getBase() {
        return base;
    }
    public ArrayList<EnemyUnit> getEnemyUnits() {
        return enemyUnits;
    }
    public ArrayList<String> getCommandLog() {
        return commandLog;
    }
    public int getBaseHealth() {
        return base.getHealth();
    }
    public int getScore() {
        return scoreManager.getScore();
    }
    public int getWave() {
        return waveManager.getCurrWave();
    }
    public int getKills() {
        return scoreManager.getKills();
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public boolean isVictory() {
        return victory;
    }
    public void setVictory(boolean victory) {
        this.victory = victory;
    }
    public int getRadarCenterX() {
        return radarCenterX;
    }
    public int getRadarCenterY() {
        return radarCenterY;
    }
    public int getRadarRadius() {
        return radarRadius;
    }
    public void playExplosion() {//use for missile PLS REMEMBER 
        sound.playExplosion();
    }
    public void playMiss() {//use for missile PLS REMEMBER 
        sound.playMiss();
    }
}
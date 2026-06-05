public class WaveManager {

    private int currWave;
    private int enemiesRemainingToDed;
    private int spawnTimer;
    private int spawnDelay;
    private int maxWaves;
    private int nextEnemyNumber;
    private SoundManager sounds;

    public WaveManager() {
    	currWave = 1;
        enemiesRemainingToDed = 8;
        spawnTimer = 0;
        spawnDelay = 150;
        maxWaves = 3;
        nextEnemyNumber = 1;
        sounds = new SoundManager();
    }

    public void update(GameWorld world) {
        if (world.isGameOver() || world.isVictory()) {
            return;
        }

        spawnTimer++;

        if (spawnTimer >= spawnDelay && enemiesRemainingToDed > 0) {
            spawnEnemy(world);
            enemiesRemainingToDed--;
            spawnTimer = 0;
        }

        if (enemiesRemainingToDed == 0 &&world.getEnemyUnits().size() == 0) {
        	if (currWave >= maxWaves) {
        	    world.setVictory(true);
        	    sounds.playVictory();
        	    world.addLog("SYS: All hostile waves defeated. Bravo-zulu, RTB for debrief.");
        	}
        	else {
            	currWave++;
                enemiesRemainingToDed = 3 + (currWave - 1) * 3;
                spawnDelay = 150 - 30*(currWave - 1);
                spawnTimer = 0;
                world.addLog("SYS: Wave " + currWave + " inbound.");
            }
        }
    }

    public void spawnEnemy(GameWorld world) {
        double angle = Math.random() * Math.PI * 2;

        int cx = world.getRadarCenterX();
        int cy = world.getRadarCenterY();
        int r = world.getRadarRadius();

        int x = cx + (int) (Math.cos(angle) * r);
        int y = cy + (int) (Math.sin(angle) * r);

        int bearing = (int) ((Math.toDegrees(angle) + 90) % 360);
        int altitude = (int) (Math.random() * (45000 - 18000 + 1) + 18000);

        int dx = x - cx;
        int dy = y - cy;
        int pixelRange = (int) Math.sqrt(dx * dx + dy * dy);
        int rangeNM = (int) ((pixelRange / (double) r) * 120);

        int enemyNumber = world.getEnemyUnits().size() + enemiesRemainingToDed;

        EnemyUnit enemy = new EnemyUnit("BANDIT " + enemyNumber, x, y);
        double speedBoost = 1.0 + (currWave - 1) * 0.50;
        enemy.setSpeed(enemy.getSpeed() * speedBoost);
        world.getEnemyUnits().add(enemy);
        world.addLog("Magic 01, Bandit, Bearing " + bearing
                + ", Alt " + altitude + " ft, Range " + rangeNM + " nm. Cleared hot.");
    }

    public boolean isFinalWaveComplete(GameWorld world) {
    	sounds.playVictory();
        return currWave >= maxWaves && enemiesRemainingToDed == 0 && world.getEnemyUnits().size() == 0;
    }

    
    
    
    
    
    public int getCurrWave() {
        return currWave;
    }
    public int getenemiesRemainingToDed() {
        return enemiesRemainingToDed;
    }
    public int getSpawnTimer() {
        return spawnTimer;
    }
}
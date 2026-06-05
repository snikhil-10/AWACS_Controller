public class ScoreManager {

    private int score;
    private int kills;

    public ScoreManager() {
        score = 0;
        kills = 0;
    }

    public void addKill() {
        kills++;
        addScore(100);
    }

    public void addScore(int points) {
        score += points;

        if (score < 0) {
            score = 0;
        }
    }

    public int getScore() {
        return score;
    }

    public int getKills() {
        return kills;
    }

}
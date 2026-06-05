public abstract class Aircraft extends GameEntity {

	protected String callsign;
	protected int health;
	protected int fuel;
	protected String state;

    public Aircraft(String callsign, double x, double y) {
        super(x, y);
        this.callsign = callsign;
        this.health = 100;
        this.fuel = 100;
        this.state = "IDLE";
    }

    public Aircraft(int hp, int fuelAmt, String callsignStr, double x, double y) {
        super(x, y);
        this.callsign = callsignStr;
        this.health = hp;
        this.fuel = fuelAmt;
        this.state = "IDLE";
    }

    
    
    
    
    
    public int getHealth() {
        return health;
    }
    public void setHealth(int healthp) {
        health = healthp;
    }
    public int getFuel() {
        return fuel;
    }
    public void setFuel(int fuelCarried) {
        fuel = fuelCarried;
    }
    public String getCallsign() {
        return callsign;
    }
    public void setCallsign(String newCallsign) {
        callsign = newCallsign;
    }
    public String getState() {
        return state;
    }
    public void setState(String newState) {
        state = newState;
    }
    public void takeDamage(int amount) {
        health -= amount;

        if (health <= 0) {
            health = 0;
            alive = false;
            state = "DESTROYED";
        }
    }
}
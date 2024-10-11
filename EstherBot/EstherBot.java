import dev.robocode.tankroyale.botapi.*;
import dev.robocode.tankroyale.botapi.events.*;

public class EstherBot extends Bot {

    // Constructor that loads the bot configuration file
    EstherBot() {
        super(BotInfo.fromFile("EstherBot.json"));
    }

    // The main method starts our bot
    public static void main(String[] args) {
        new EstherBot().start();
    }

    // Called when a new round is started
    @Override
    public void run() {
        // Set a random movement pattern
        while (isRunning()) {
            // Move in a random direction
            double distance = Math.random() * 100 + 50; // Move between 50 and 150
            double angle = Math.random() * 360; // Random angle
            turnRight(angle);
            forward(distance);

            // Spin the gun and fire at enemies
            turnGunRight(360);
        }
    }

    // Called when another bot is scanned
    @Override
    public void onScannedBot(ScannedBotEvent e) {
        // Get the distance to the scanned bot
        double distance = e.getDistance();
        
        // If the enemy is close, evade by turning away and moving back
        if (distance < 200) {
            evade(e);
        } else {
            fire(1); // Otherwise, fire at the enemy
        }
    }

    // Called when we are hit by a bullet
    @Override
    public void onHitByBullet(HitByBulletEvent e) {
        // Calculate the bearing to the direction of the bullet
        var bearing = calcBearing(e.getBullet().getDirection());

        // Turn 90 degrees to the bullet direction based on the bearing
        turnLeft(90 - bearing);
        forward(50); // Move forward after evading
    }

    // Method to evade an enemy
    private void evade(ScannedBotEvent e) {
        // Get the bearing to the enemy
        double enemyBearing = e.getBearing();

        // Turn away from the enemy
        turnLeft(90 + enemyBearing);
        // Move backward for a short distance
        back(100);
    }
}


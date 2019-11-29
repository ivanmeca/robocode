package ntopus;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
//import java.awt.Color;
// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html
/**
 * IvanRobot - a robot by (your name here)
 */
public class IvanRobot extends Robot
{
    int count = 0; // Keeps track of how long we've
    // been searching for our target
    double gunTurnAmt; // How much to turn our gun when searching
    String trackName; // Name of the robot we're currently tracking
    double lastReceivedShootPower = 3;
    boolean battleInCourse;
        // Prepare gun

    /**
     * run: IvanRobot's default behavior
     */
    public void run() {

        trackName = null; // Initialize to not tracking anyone
        setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
        gunTurnAmt = 10; // Initialize gunTurn to 10
        battleInCourse = false;
        // Robot main loop
        while(true) {
            // Replace the next 4 lines with any behavior you would like

                turnGunRight(gunTurnAmt);
                // Keep track of how long we've been looking
                count++;
                // If we've haven't seen our target for 2 turns, look left
                if (count > 2) {
                    gunTurnAmt = -10;
                }
                // If we still haven't seen our target for 5 turns, look right
                if (count > 5) {
                    gunTurnAmt = 10;
                }
                // If we *still* haven't seen our target after 10 turns, find another target
                if (count > 11) {
                    trackName = null;
                }
            }
    }
    /**
     * onScannedRobot: What to do when you see another robot
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        if (e.getDistance() > 150) {
            gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
            turnGunRight(gunTurnAmt); // Try changing these to setTurnGunRight,
            turnRight(e.getBearing()); // and see how much Tracker improves...
            // (you'll have to make Tracker an AdvancedRobot)
             ahead(e.getDistance() - 140);
        }else{
            this.battleInCourse = true;
        }
        // Our target is close.
        gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
        turnGunRight(gunTurnAmt);
        out.println("Firing " + this.lastReceivedShootPower+ " shoot");
        fire(this.lastReceivedShootPower*2);
    }
    /**
     * onHitByBullet: What to do when you're hit by a bullet
     */
    public void onHitByBullet(HitByBulletEvent e) {
        // Replace the next line with any behavior you would like
        //turnRight(30);
        //turnGunLeft(30); // Try changing these to setTurnGunRight,
        out.println("Received " + e.getPower() + " shoot");
        this.lastReceivedShootPower = e.getPower();
        back(20);
    }

    /**
     * onHitWall: What to do when you hit a wall
     */
    public void onHitWall(HitWallEvent e) {
        // Replace the next line with any behavior you would like
        back(20);
        //turnRight(180);
    }

}
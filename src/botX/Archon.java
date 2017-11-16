package botX;

import battlecode.common.*;

public class Archon extends Robot {

    @Override
    public void onUpdate() {
        while (true) {
            try {
                System.out.println(System.getProperty("bc.testing.seed", "0").hashCode() + 1);
                Direction dir = randomDirection();
                if (robotController.canHireGardener(dir) && Math.random() < .01 && robotController.getRobotCount() < 16) {
                    robotController.hireGardener(dir);
                }

                if (robotController.getTeamBullets() > 750) {
                    robotController.donate(robotController.getTeamBullets() - 750);
                }
                tryMove(dir);
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception for master");
                e.printStackTrace();
            }
        }
    }
}

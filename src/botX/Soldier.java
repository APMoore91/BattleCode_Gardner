package botX;
import battlecode.common.*;

public class Soldier extends Robot{
    public void onUpdate(){
        Direction soldierDir = null;

        while(true){
            try{
                RobotInfo[] enemiesInSight = robotController.senseNearbyRobots(robotController.getType().sensorRadius, enemy);
                Direction dir = null;
                if(enemiesInSight.length > 0 && enemiesInSight[0].getType() != RobotType.LUMBERJACK){
                    soldierDir = new Direction(robotController.getLocation(), enemiesInSight[0].location);
                }
                else{
                    dir = randomDirection();
                }
                if (soldierDir == null) {
                    soldierDir = dir;
                }
                if (tryMove(soldierDir)) {
                    System.out.println("moved");
                } else {
                    soldierDir = randomDirection();
                    tryMove(soldierDir);
                }

                if(enemiesInSight.length > 0){
                    if(robotController.canFireSingleShot() && robotController.getTeamBullets() > 500){
                        robotController.fireSingleShot(new Direction(robotController.getLocation(), enemiesInSight[0].location));
                    }
                }
                Clock.yield();
            }catch(Exception e){
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}

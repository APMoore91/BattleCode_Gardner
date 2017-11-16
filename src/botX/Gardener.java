package botX;

import battlecode.common.*;

class Gardener extends Robot {

    public void onUpdate() {
        boolean settled = false;
        Direction gardnerDir = null;

        while (true) {
            try {

                Direction dir = randomDirection();
                if (gardnerDir == null) {
                    gardnerDir = dir;
                }
                if (!(robotController.isCircleOccupiedExceptByThisRobot(robotController.getLocation(), robotController.getType().bodyRadius * 6.0f))) {
                    settled = true;
                    if (robotController.canPlantTree(dir)) {
                        robotController.plantTree(dir);
                    }
                }
                if (settled) {
                    if (robotController.canPlantTree(dir) && robotController.senseNearbyTrees().length < 3) {
                        robotController.plantTree(dir);
                    }
                    if(robotController.canBuildRobot(RobotType.SOLDIER, dir) && robotController.getRobotCount() < 25){
                        robotController.buildRobot(RobotType.SOLDIER, dir);
                    }
                }

                TreeInfo[] trees = robotController.senseNearbyTrees(robotController.getType().bodyRadius * 2, robotController.getTeam());
                TreeInfo minHealthTree = null;
                for (TreeInfo tree : trees) {
                    if (tree.health < 70) {
                        if (minHealthTree == null || tree.health < minHealthTree.health) {
                            minHealthTree = tree;
                        }
                    }
                }
                if (minHealthTree != null) {
                    robotController.water(minHealthTree.ID);
                }

                if (!settled) {
                    if (tryMove(gardnerDir)) {
                        System.out.println("moved");
                    } else {
                        gardnerDir = randomDirection();
                        tryMove(gardnerDir);
                    }
                }

                Clock.yield();
            } catch (Exception e) {
                System.out.println("A robotController Exception");
                e.printStackTrace();
            }
        }
    }
}

package com.game.model.Enum;

import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum Direction {
    up,
    down,
    right,
    left,
    none;

    private static  List<Direction>  list = new ArrayList<>();
    private static  List<Integer[]>  change = new ArrayList<>();

    static {
        list.add(up);
        list.add(down);
        list.add(left);
        list.add(right);
        list.add(none);
        change.add(new Integer[]{0,-1});
        change.add(new Integer[]{0,1});
        change.add(new Integer[]{1,0});
        change.add(new Integer[]{-1,0});
        change.add(new Integer[]{0,0});
    }

    public static String getNameByInstance(Direction direction) {
        switch (direction) {
            case right:
                return "right";
            case left:
                return "left";
            case down:
                return "down";
            case up:
                return "up";
            case none:
                return "none";
            default:
                return null;
        }
    }

    public static Direction getDirectionByname(String name) {
        switch (name) {
            case "up":
                return Direction.up;
            case "down":
                return Direction.down;
            case "right":
                return Direction.right;
            case "left":
                return Direction.left;
            case "none":
                return Direction.none;
            default: return null;
        }
    }

    public static List<Direction> getDirectionList(){
        return list;
    }

    public static  Integer[] getChangeByDirection(Direction direction){
        switch (direction){
            case up: return change.get(0);
            case down: return change.get(1);
            case right: return change.get(2);
            case left: return change.get(3);
            case none: return change.get(4);
        }
        return null;
    }

}

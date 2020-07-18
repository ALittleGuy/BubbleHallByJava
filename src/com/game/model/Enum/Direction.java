package com.game.model.Enum;

public enum Direction {
    up,
    down,
    right,
    left;

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
            default: return null;
        }
    }

}

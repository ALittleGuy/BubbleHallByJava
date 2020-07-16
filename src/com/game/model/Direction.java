package com.game.model;

public enum Direction {
    up,
    down,
    right,
    left;

     public static String getNameByInstance(Direction direction) {
        switch (direction){
            case right:return "right";
            case left:return "left";
            case down:return "down";
            case up:return "up";
            default:return null;
        }
    }

}

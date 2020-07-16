package com.game.model;

public enum WallType {
    GRASS(1),
    IRON(4),
    RIVER(3),
    BRICK(2);

    private int life;

    WallType(int i) {
        this.life = i;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}

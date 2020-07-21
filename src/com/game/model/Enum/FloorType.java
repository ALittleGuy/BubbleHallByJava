package com.game.model.Enum;

public enum  FloorType {

    Road1(0,0 , 40 ,40) ,
    Road2 (40 , 0 , 80 , 40),
    Road3 (80,0 , 120 , 40),
    Block (160 ,0 , 200 ,40),
    Block1 (200,0,240 ,40),
    Grass (240 , 0 ,280 , 40),
    Grass1 (280,0,320 , 40);

    private int x1;
    private int y1;
    private int x2;
    private int y2;

    FloorType(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }
}

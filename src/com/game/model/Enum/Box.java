package com.game.model.Enum;

import java.awt.*;
import java.util.concurrent.TransferQueue;

public enum Box {
    //不可破坏，可隐藏
    GRASS(0, 112, 32, 144, Box.MAP, false),

    //不可破坏
    TREE(32, 7, 65, 64, Box.MAP, false),
    //    IRON_BLOCK,
//    ROAD_BLOCK,
    YELLOW_ROOM(64,16 , 96,64 , Box.BOX , false),
    BLUER_ROOM(32,64,54,112, Box.MAP , false),
//    //可破坏
//
    YELLOW_BLOCK(0, 0, 32, 32, Box.BOX, true),
    RED_BLOCK(32, 0, 64, 32, Box.BOX, true),
    PAPER_BLOCK(98,0,128,32,Box.MAP,true);
//    BLUE_BLOCK,
//    DEEPBLUE_BLOCK;


    public Integer x1, y1, x2, y2, adjust;
    public String type;
    public boolean isBreaked;
    public static final String MAP = "floor";
    public static final String BOX = "box";

    Box(Integer x1, Integer y1, Integer x2, Integer y2, String type, boolean isBreaked) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.type = type;
        this.isBreaked = isBreaked;
    }

    public static Box getBoxByString(String s) {
        switch (s) {
            case "GRASS":
                return GRASS;
            case "TREE":
                return TREE;
            case "YELLOW_BLOCK":
                return YELLOW_BLOCK;
            case "RED_BLOCK":
                return RED_BLOCK;
            default:
                return null;
        }
    }


}

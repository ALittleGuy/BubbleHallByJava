package com.game.model;

import com.game.model.Enum.WallType;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj {
    @Override
    public void showElement(Graphics graphics) {

    }


//    private WallType wallType;
//
//    @Override
//    public void showElement(Graphics graphics) {
//        graphics.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
//
//    }
//
//    @Override
//    public ElementObj createElement(String str) {
//        String strs[] = str.split(",");
//        ImageIcon imageIcon = new ImageIcon();
//        switch (strs[0]){
//            case "GRASS":imageIcon= new ImageIcon("src/res/image/image/wall/grass.png");wallType = WallType.GRASS;break;
//            case "IRON":imageIcon= new ImageIcon("src/res/image/image/wall/iron.png");wallType=WallType.IRON;break;
//            case "RIVER":imageIcon= new ImageIcon("src/res/image/image/wall/river.png");wallType=WallType.RIVER;break;
//            case "BRICK":imageIcon= new ImageIcon("src/res/image/image/wall/brick.png");wallType=WallType.BRICK;break;
//        }
//
//        this.setX(Integer.parseInt(strs[1]));
//        this.setY(Integer.parseInt(strs[2]));
//        this.setW(imageIcon.getIconWidth());
//        this.setH(imageIcon.getIconHeight());
//        this.setIcon(imageIcon);
//        this.hp = wallType.getLife();
//        if(wallType == WallType.RIVER){
//            hp=-1;
//        }
//        return this;
//    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
//
//
//    //设置死亡状态和hp变化
//    @Override
//    public void setLiveStatus(boolean liveStatus) {
//        if(!liveStatus){
//            hp--;
//            System.out.println(hp);
//        }
//        if(hp==0){
//            super.setLiveStatus(false);
//        }
//    }
}

package com.game.model;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.Enum.Box;
import com.game.model.Enum.GameProps;

import javax.swing.*;
import java.awt.*;

public class BoxObj extends ElementObj {
    private boolean isBreakable;
    private GameProps gameProps;
    private Box boxType;

    @Override
    public void showElement(Graphics graphics) {
        int up = 0;
        int down = 0;
        switch (boxType) {
            case TREE:
                up = 25;
                break;
            case RED_BLOCK:
            case YELLOW_BLOCK:
                down = 10;
                break;
        }
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW(), this.getY() * this.getH() - up,
                this.getX() * this.getW() + this.getH(),
                this.getY() * this.getH() + this.getH() + down,
                boxType.x1, boxType.y1,
                boxType.x2, boxType.y2, null
        );
    }

    public BoxObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.boxType = Box.valueOf(strs[2]);
        this.isBreakable = boxType.isBreaked;
        ImageIcon imageIcon = GameLoad.playIconMap.get(boxType.type);
        this.setIcon(imageIcon);
        this.setW(32);
        this.setH(32);
        return this;
    }

    @Override
    public Rectangle getRectangel() {
        return new Rectangle(this.getX() * 32, this.getY() * 32, this.getW(), this.getH());
    }

    @Override
    public boolean impact(ElementObj elementObj) {
        return this.getRectangel().intersects(elementObj.getRectangel());
    }

    @Override
    public String toString() {
        return "BoxObj{" +
                "x=" + this.getX() +
                ",y=" + this.getY() +
                ",isBreakable=" + isBreakable +
                ", gamepros=" + gameProps +
                ", boxType=" + boxType +
                '}';
    }

    @Override
    public void setLiveStatus(boolean liveStatus) {
        if (!isBreakable) {
            return;
        }
        super.setLiveStatus(liveStatus);
        if (!liveStatus) {
            ModelManager modelManager = ModelManager.getManager();
            modelManager.getElementsByKey(GameElement.MAP)[this.getX()][this.getY()] = null;
            ElementObj prop = modelManager.getElementsByKey(GameElement.PROP)[this.getX()][this.getY()];
            if(prop!=null){
                modelManager.addElement(prop,GameElement.PROP_AFTER_BOOM , this.getX() , this.getY());
                modelManager.remove(GameElement.PROP , this.getX() , this.getY());
            }

        }
    }

    public boolean isBreakable() {
        return isBreakable;
    }

//    public void check(){
//        System.out.println(this.getX()+","+this.getY());
//    }


    public Box getBoxType() {
        return boxType;
    }

}


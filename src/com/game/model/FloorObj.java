package com.game.model;

import com.game.manager.GameLoad;
import com.game.model.Enum.FloorType;

import javax.swing.*;
import java.awt.*;

public class FloorObj extends ElementObj {


    private FloorType floorType ;
    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW(), this.getY() * this.getH(),
                this.getX() * this.getW() + 32, this.getY() * this.getH() + 32,
                floorType.getX1(), floorType.getY1(), floorType.getX2(), floorType.getY2(), null
        );
    }

    @Override
    public ElementObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.floorType = FloorType.valueOf(strs[2]);
        ImageIcon imageIcon = GameLoad.playIconMap.get("Floor");
        this.setIcon(imageIcon);
        this.setW(32);
        this.setH(32);
        return this;
    }


}

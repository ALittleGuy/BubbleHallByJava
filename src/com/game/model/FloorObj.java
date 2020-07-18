package com.game.model;

import com.game.manager.GameLoad;

import javax.swing.*;
import java.awt.*;

public class FloorObj extends ElementObj {


    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW(), this.getY() * this.getH(),
                this.getX() * this.getW() + 32, this.getY() * this.getH() + 32,
                0, 0, 32, 32, null
        );
    }

    @Override
    public ElementObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        ImageIcon imageIcon = GameLoad.playIconMap.get("floor");
        this.setIcon(imageIcon);
        this.setW(32);
        this.setH(32);
        return this;
    }


}

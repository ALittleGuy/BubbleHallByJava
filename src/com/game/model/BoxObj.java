package com.game.model;

import com.game.model.Enum.Box;
import com.game.model.Enum.GAMEPROS;
import com.game.tools.Pair;

import javax.swing.*;
import javax.swing.text.html.StyleSheet;
import java.awt.*;

public class BoxObj extends ElementObj {
    private boolean isBreakable;
    private GAMEPROS gamepros;
    private Box boxType;

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW(), this.getY() * this.getH(),
                this.getX() * this.getW() + this.getH(),
                this.getY() * this.getH() + this.getH(),
                boxType.x1, boxType.y1,
                boxType.x2, boxType.y2, null
        );
    }

    public BoxObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.boxType = Box.getBoxByString(strs[2]);
        this.isBreakable = boxType.isBreaked;
        ImageIcon imageIcon = new ImageIcon(boxType.type);
        this.setIcon(imageIcon);
        this.setW(32);
        this.setY(32);
        return this;
    }
}


package com.game.model;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.model.Enum.Box;
import com.game.model.Enum.GameProps;

import javax.swing.*;
import java.awt.*;

public class PropObj extends ElementObj{

    private GameProps gameProps;
    private int imgX;
    private long localGameTime;

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW(), this.getY() * this.getH() - 14,
                this.getX() * this.getW() + this.getW(),
                this.getY() * this.getH() + this.getH(),
                0 + imgX * 32, 0,
                32 + imgX * 32, 48, null
        );

    }

    @Override
    protected void updateImage(long gameTime) {
        if (gameTime - localGameTime > 30) {
            imgX++;
            if (imgX > 3) {
                imgX = 0;
            }
            localGameTime = gameTime;
        }
    }

    /**
     *
     * @param str "x坐标 , y坐标 , 道具类型 , "
     * @return
     */
    @Override
    public ElementObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.gameProps = GameProps.valueOf(strs[2]);
        ImageIcon imageIcon = GameLoad.playIconMap.get(gameProps.name());
        this.setIcon(imageIcon);
        this.setW(32);
        this.setH(32);
        imgX = 0;
        localGameTime = 0;

        return this;
    }
}

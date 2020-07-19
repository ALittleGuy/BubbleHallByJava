package com.game.model;

import com.game.manager.GameLoad;
import com.game.model.Enum.Direction;
import com.sun.org.apache.xml.internal.dtm.ref.DTMDefaultBaseIterators;

import javax.swing.*;
import java.awt.*;

public class BoomPiece extends ElementObj {

    /**
     * 切图左上角
     */
    private Point point1;
    private Direction direction;
    private boolean isTheLast;



    @Override
    public void showElement(Graphics graphics) {
        int x = (int) point1.getX();
        int y = (int) point1.getY();
        graphics.drawImage(this.getIcon().getImage(), this.getX()*this.getW(),
                this.getY()*this.getH() , this.getX()*this.getW()+this.getW(),
                this.getY()*this.getH()+this.getH() , x,y,
                x+this.getW() , y+this.getH() , null
                );
    }


    /**
     *
     * @param str x 坐标(0-15) , y坐标(0-15) , 方向 , 是否最后一个
     * @return
     */
    @Override
    public ElementObj createElement(String str) {
        String strs[] = str.split(",");
        this.setX(Integer.parseInt(strs[0]));
        this.setY(Integer.parseInt(strs[1]));
        this.direction= Direction.getDirectionByname(strs[2]);
        this.isTheLast = Boolean.valueOf(strs[3]);
        ImageIcon imageIcon = GameLoad.playIconMap.get("boomPiece");
        this.setIcon(imageIcon);
        point1 = new Point();
        this.setW(32);
        this.setH(32);
        if(!isTheLast){
            switch (direction){
                case up:point1.setLocation(352,160);break;
                case right:point1.setLocation(544,352);break;
                case left:point1.setLocation(736,352);break;
                case down:point1.setLocation(352,352);break;
                case none:point1.setLocation(160,352);break;
            }
        }else {
            switch (direction){
                case up:point1.setLocation(160,448);break;
                case right:point1.setLocation(160,160);break;
                case left:point1.setLocation(544,160);break;
                case down:point1.setLocation(736,160);break;
                case none:point1.setLocation(160,352);break;

            }
        }
        boom(this);

        return this;
    }

    private void boom(BoomPiece boomPiece) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                boomPiece.setLiveStatus(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}

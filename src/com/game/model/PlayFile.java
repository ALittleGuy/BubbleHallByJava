package com.game.model;

import com.game.manager.ModelManager;
import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.reflect.Field;

/**
 * 子弹类
 * 玩家的实体对象,由玩家对象调用和床创建,继承自ElementObj , 重写showElement()
 * 选择性重写move add updateImage
 * 思考并定义子类特有的属性
 *
 * @author prh
 */
public class PlayFile extends ElementObj {

    //方向
    private Direction direction;

    //速度
    private int speed;

    //最大距离
    private int maxLength;

    //伤害值
    private int injure;

    private long time = 0;

    //
    @Override
    public void showElement(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillOval(this.getX(), this.getY(), this.getW(), this.getH());


    }

    public PlayFile() {
    }


    //封装创建对象的过程,外界只需传输必要的对象参数
    public ElementObj createElement(String str) {

        String[] strs = str.split(",");
        this.setW(10);
        this.setH(10);
        for (String s : strs) {
            String[] str1 = s.split(":");
            switch (str1[0]) {
                case "x":
                    this.setX(Integer.parseInt(str1[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(str1[1]));
                    break;
                case "direction":
                    this.direction = Direction.valueOf(str1[1]);
                    break;
                default:
                    break;

            }
//            Class mClass = PlayFile.class;
//            try {
//                Field field = mClass.getField(str1[0]);
//                field.set();
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }


        }

        this.setW(10);
        this.setH(10);
        this.speed = 2;
        time = 0;
        return this;

    }


    @Override
    public String toString() {
        return "PlayFile{" +
                "direction=" + direction +
                ", speed=" + speed +
                ", maxLength=" + maxLength +
                ", injure=" + injure +
                ", x=" + this.getX() +
                ", y=" + this.getY() +
                ", direction" + this.direction +
                '}';
    }

    @Override
    protected void move() {
        super.move();
        if (this.getX() < 0 || this.getX() > 500 || this.getY() < 0 || this.getY() > 600) {
            this.setLiveStatus(false);
        }

        switch (direction) {
            case up:
                this.setY(this.getY() - speed);
                break;
            case down:
                this.setY(this.getY() + speed);
                break;
            case left:
                this.setX(this.getX() - speed);
                break;
            case right:
                this.setX(this.getX() + speed);
                break;
            default:
                break;
        }

    }

    @Override
    protected void updateImage(long gameTime) {
        super.updateImage(gameTime);
//        if (gameTime - time > 20) {
//            time = gameTime;
//            this.setW(getW()+5);
//            this.setH(getH()+5);
//
//        }
    }

    /**
     * 子弹消亡情况
     * 1.出边界
     * 2.碰撞
     * 3.玩家使用全屏清除道具
     * <p>
     * 处理方式:当达到死亡的条件时只进行修改死亡状态的操作.
     */


    @Override
    public void die() {
        super.die();
        /**
         * 死亡动画等等;
         */
    }
}

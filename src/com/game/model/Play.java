package com.game.model;

import com.game.controller.GameThread;
import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;

import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Phaser;

/**
 * The type Play.
 */
public class Play extends ElementObj {


    /**
     * 移动属性：
     * １．单属性配合　方向枚举类型使用；一次只能一个方向
     * ２．双属性　上下和左右　配合布尔值使用　例如：True代表上 False代表下  需要另一个变量确定是否按下方向键
     * 约定0代表不动 1代表上 2代表下
     * 3.4属性 上下左右都可以 True代表移动 false代表不移动
     * 同时按上下的时候 后按的会重置先按的
     * <p>
     * 问题:1.图片要读入到内存中:加载器 临时处理方式 , 手动编写存储到内存中
     * 2.是么时候进行修改图片
     * 3.图片应该使用什么集合去存储
     */


    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Direction direction = Direction.up;

    private boolean attackStatus = false;

    private int speed;

    //枚举类型配合移动扩展

    //图片集合

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    /**
     * Instantiates a new Play.
     *
     * @param x    the x
     * @param y    the y
     * @param w    the w
     * @param h    the h
     * @param icon the icon
     */
    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        this.speed=1;


    }


    /**
     * 重写键盘监听事件
     *
     * @param bl  true 代表按下
     * @param key 代表键盘的code值
     */
    @Override
    public void keyClick(Boolean bl, int key) {

        super.keyClick(bl, key);
        if (bl) {
            switch (key) {
                case 37:
                    this.right = false;
                    this.down = false;
                    this.up = false;
                    this.left = true;
                    this.direction = Direction.left;
                    break;
                case 38:
                    this.right = false;
                    this.down = false;
                    this.left = false;
                    this.up = true;

                    this.direction = Direction.up;
                    break;
                case 39:
                    this.up = false;
                    this.down = false;
                    this.left = false;
                    this.right = true;

                    this.direction = Direction.right;
                    break;
                case 40:
                    this.left = false;
                    this.right = false;
                    this.up = false;
                    this.down = true;
                    this.direction = Direction.down;
                    break;
                case 32:
                    this.attackStatus = true;
                    break;
                default:
                    break;
            }
        } else {
            switch (key) {
                case 37:
                    this.left = false;
                    break;
                case 38:
                    this.up = false;
                    break;
                case 39:
                    this.right = false;
                    break;
                case 40:
                    this.down = false;
                    break;
                case 32:
                    this.attackStatus = false;
                    break;

                default:
                    break;

            }

        }
    }

    /**
     * 覆盖父类的移动方法
     */
    @Override
    public void move() {
        if (this.left && this.getX() > 0) {
            this.setX(this.getX() - speed);
        }
        if (this.right && this.getX() <500) {
            this.setX(this.getX() + speed);
        }
        if (this.up && this.getY() >0) {
            this.setY(this.getY() - speed);
        }
        if (this.down && this.getY() < 500 ) {
            this.setY(this.getY() + speed);
        }
    }

    /**
     * 覆盖父类的移动方法
     * 更新方向
     */
    @Override
    public void updateImage(long gameTime) {
        this.setIcon(GameLoad.imageIconMap.get(direction));
    }


    /**
     * 覆盖父类的add方法
     * 添加子弹
     * <p>
     * 发射者的坐标位置 , 发射者的方向
     */
    @Override
    public void add() {
        //如果不是发射状态 直接return
        if (!this.attackStatus) {
            return;
        }
        this.attackStatus=false;
        ElementObj elementObj = new PlayFile().createElement(this.toString());
        ModelManager.getManager().addElement(elementObj, GameElement.PLAYFILE);
    }


    //此处直接使用toString , 建议在定义一个方法
    @Override
    public String toString() {
        int x = this.getX();
        int y = this.getY();
        switch (direction) {
            case right:
                x += 50;
                y+=20;
                break;
            case left:
                y += 20;
                break;
            case down:
                y += 50;
                x += 20;
                break;
            case up:
                x += 20;
                break;

        }
        return "x:" + x + ",y:" + y + ",direction:" + Direction.getNameByInstance(this.direction);
    }
}

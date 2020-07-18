package com.game.model;

import com.sun.org.apache.regexp.internal.RE;

import javax.swing.*;
import java.awt.*;


/**
 * The type Element obj.
 *
 * @author prh
 */
public abstract class ElementObj {

    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    /**
     * 生存状态:true代表存活,false代表死亡
     * 可用枚举值了来定义各种状态(生存\失望\隐身\无敌)
     */
    private boolean liveStatus = true;


    /**
     * Instantiates a new Element obj with all Params.
     *
     * @param x    the x
     * @param y    the y
     * @param w    the w
     * @param h    the h
     * @param icon the icon
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    /**
     * Instantiates a new Element obj.
     */
    public ElementObj() {

    }


    /**
     * 模板模式 , 在模板模式中定义对象的先后执行顺序,有子类选择性重写方法
     * 1.移动 2.换装 3.子弹发射
     */

    public final void model(long gameTime) {
        //先换装 再移动 再发射子弹
        updateImage(gameTime);
        move();
        add();
    }
    protected void move() {

    }

    protected void add() {

    }

    protected void updateImage(long gameTime) {

    }


    /**
     * 死亡方法
     * 死亡也是一个对象
     *
     */
    public void die(){

    }


    /**
     * 抽象方法
     *
     * @param graphics the graphics
     */
    public abstract void showElement(Graphics graphics);


    /**
     * 通过用父类监听键盘事件或者使用接口的方式监听键盘事件
     * 只有需要实现键盘监听的子类需要重写该方法
     *
     * @param bl  true 代表按下
     * @param key 代表键盘的code值
     *            扩展:两个方法 一个接受按下 一个接受松开
     */
    public void keyClick(Boolean bl, int key) {

    }

    /**
     * 需要移动的子类则实现该方法
     */
    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() { return x; }
    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
        this.y = y;
    }
    /**
     * Gets w.
     *
     * @return the w
     */
    public int getW() {
        return w;
    }
    /**
     * Sets w.
     *
     * @param w the w
     */
    public void setW(int w) {
        this.w = w;
    }
    /**
     * Gets h.
     *
     * @return the h
     */
    public int getH() {
        return h;
    }
    /**
     * Sets h.
     *
     * @param h the h
     */
    public void setH(int h) {
        this.h = h;
    }
    /**
     * Gets icon.
     *
     * @return the icon
     */
    public ImageIcon getIcon() {
        return icon;
    }
    /**
     * Sets icon.
     *
     * @param icon the icon
     */
    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }


    public ElementObj createElement(String str) {
        return null;
    }

    /**
     * Is live status boolean.
     *
     * @return the boolean
     */
    public boolean isLiveStatus() {
        return liveStatus;
    }

    /**
     * Sets live status.
     *
     * @param liveStatus the live status
     */
    public void setLiveStatus(boolean liveStatus) {
        this.liveStatus = liveStatus;
    }


    /**
     * 返回对象的碰撞矩形
     * @return
     */
    public Rectangle getRectangel(){
        return new Rectangle(x,y,w,h);
    }


    /**
     * 碰撞方法
     * @param elementObj 检测物体
     * @return boollean 返回True说明有碰撞 , 返回False 说明没有碰撞
     */
    public boolean impact(ElementObj elementObj){
        return this.getRectangel().intersects(elementObj.getRectangel());
    }

    @Override
    public String toString() {
        return "ElementObj{" +
                "x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", icon=" + icon +
                ", liveStatus=" + liveStatus +
                '}';
    }
}


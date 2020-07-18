package com.game.model;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.Enum.Direction;
import com.sun.xml.internal.ws.api.client.WSPortInfo;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.StringJoiner;

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
    private Direction impactDirection;
    private Set<Integer> keySet ;

    private long time = 0;
    private int imgX;
    private int imgY;
    private boolean moveStatus;
    //枚举类型配合移动扩展

    //图片集合

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getX()+32,this.getY() +32,
                27 + (imgX * 100), 44 + imgY * 100,
                72 + (imgX * 100), 100 + imgY * 100,
                null);
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
        this.speed = 1;
    }

    public Play() {

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
                case 37  :
                    this.right = false;
                    this.down = false;
                    this.up = false;
                    this.left = true;
                    this.direction = Direction.left;
                    keySet.add(key);
                    moveStatus = true;
                    break;
                case 38:
                    this.right = false;
                    this.down = false;
                    this.left = false;
                    this.up = true;
                    this.direction = Direction.up;
                    keySet.add(key);
                    moveStatus = true;
                    break;
                case 39:
                    this.up = false;
                    this.down = false;
                    this.left = false;
                    this.right = true;
                    this.direction = Direction.right;
                    keySet.add(key);
                    moveStatus = true;
                    break;
                case 40:
                    this.left = false;
                    this.right = false;
                    this.up = false;
                    this.down = true;
                    this.direction = Direction.down;
                    keySet.add(key);
                    moveStatus = true;
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
                    keySet.remove(37);
                    break;
                case 38:
                    this.up = false;
                    keySet.remove(38);
                    break;
                case 39:
                    this.right = false;
                    keySet.remove(39);
                    break;
                case 40:
                    this.down = false;
                    keySet.remove(40);
                    break;
                case 32:
                    this.attackStatus = false;
                    break;
                default:
                    break;
            }
            if(keySet.isEmpty()){
                moveStatus = false;
            }

        }

    }

    /**
     * 覆盖父类的移动方法
     */
    @Override
    public void move() {

            if (this.left && this.getX() - speed >= 0 && impactDirection != direction) {
                this.imgY = 1;
                this.setX(this.getX() - speed);

            }
            if ( this.right && this.getX() + speed <= 770 && impactDirection != direction) {
                this.imgY = 2;
                this.setX(this.getX() + speed);
            }
            if (this.up  && this.getY() - speed >= 0 && impactDirection != direction) {
                this.imgY = 3;
                this.setY(this.getY() - speed);
            }
            if (this.down  && this.getY() + speed <= 550 && impactDirection != direction) {
                this.imgY = 0;
                this.setY(this.getY() + speed);
        }

    }

    /**
     * 覆盖父类的移动方法
     * 更新方向
     */
    @Override
    public void updateImage(long gameTime) {
        if(gameTime-time>10) {
            time = gameTime;
            if (moveStatus) {
                imgX++;
                if (imgX > 3) {
                    imgX = 0;
                }
            } else {
                imgX = 0;
            }
        }

    }


    /**
     * 覆盖父类的add方法
     * 添加子弹
     * <p>
     * 发射者的坐标位置 , 发射者的方向
     */
//    @Override
//    public void add() {
//        //如果不是发射状态 直接return
//        if (!this.attackStatus) {
//            return;
//        }
//        this.attackStatus = false;
//        ElementObj elementObj = new PlayFile().createElement(this.toString());
//        ModelManager.getManager().addElement(elementObj, GameElement.PLAYFILE );
//    }


    //此处直接使用toString , 建议在定义一个方法
    @Override
    public String toString() {
        int x = this.getX();
        int y = this.getY();
        switch (direction) {
            case right:
                x += 50;
                y += 20;
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

    public ElementObj createElement(String str) {
        String[] strs = str.split(",");
        this.setX(new Integer(strs[0]));
        this.setY(new Integer(strs[1]));
        ImageIcon icon = GameLoad.playIconMap.get("play");
        this.setW(22);
        this.setH(12);
        this.setIcon(icon);
        this.direction = Direction.up;
        this.imgX = 0;
        this.imgY = 1;
        this.speed = 1;
        this.time = 0;
        keySet = new HashSet<>();
        this.impactDirection=Direction.none;
        return this;
    }


    @Override
    public Rectangle getRectangel() {
        return new Rectangle(this.getX()+11,this.getY()+11,10,10);
    }

    public void onImpact(Rectangle target){
//        switch (direction){
//            case up: this.setY(this.getY()+1);break;
//            case down: this.setY(this.getY()-1);break;
//            case right: this.setX(this.getX()-1);break;
//            case left: this.setX(this.getX()+1);break;
//        }
        Rectangle rectangle = new Rectangle(this.getX()+10,this.getY()+11,10,10);
        if(!rectangle.intersects(target)){
            this.setX(this.getX()-1);
        }
        rectangle = new Rectangle(this.getX()+12,this.getY()+11,10,10);
        if(!rectangle.intersects(target)){
            this.setX(this.getX()+1);
        }
        rectangle = new Rectangle(this.getX()+11,this.getY()+10,10,10);
        if(!rectangle.intersects(target)){
            this.setY(this.getY()-1);
        }
        rectangle = new Rectangle(this.getX()+11,this.getY()+12,10,10);
        if(!rectangle.intersects(target)){
            this.setY(this.getY()+1);
        }

    }

    @Override
    public boolean impact(ElementObj elementObj) {
        return super.impact(elementObj);
    }
}

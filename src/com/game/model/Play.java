package com.game.model;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.Enum.Box;
import com.game.model.Enum.Direction;
import com.sun.media.sound.SoftPointResampler;

import javax.swing.*;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Integer> keySet;
    private long time = 0;
    private int imgX;
    private int imgY;
    private boolean moveStatus;
    private Point poingLeftUp;
    private Point pointRightDown;
    /**
     * 道具属性
     */
    private boolean reverseStatus;
    private int superCard;
    private boolean stuckStatus;
    private boolean boomStuckStatus;
    private boolean isEnemy;


    //子弹数量,初始值为1
    private int boomNum;
    private int boomLength;
    //枚举类型配合移动扩展

    //图片集合

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY() - 10,
                this.getX() + 32, this.getY() + 32,
                (int) poingLeftUp.getX() + (imgX * 100), (int) poingLeftUp.getY() + imgY * 100,
                (int) pointRightDown.getX() + (imgX * 100), (int) pointRightDown.getY() + imgY * 100,
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
        if (isEnemy) {
            int key1 = key;
            switch (key) {
                case 65:
                    key1 = 37;
                    break;
                case 87:
                    key1 = 38;
                    break;
                case 68:
                    key1 = 39;
                    break;
                case 83:
                    key1 = 40;
                    break;
                case 70:
                    key1 = 17;
                    break;
                default:
                    key1 = 0;
                    break;
            }
            onKeyClick(bl, key1);
        } else {
            onKeyClick(bl, key);
        }
    }

    private void onKeyClick(Boolean bl, int key) {
//        if(isBoomStuckStatus() || isStuckStatus()){
//            return;
//        }
        int action = key;
        if (isReverseStatus()) {

            switch (key) {
                case 37:
                    action = 39;
                    break;
                case 38:
                    action = 40;
                    break;
                case 39:
                    action = 37;
                    break;
                case 40:
                    action = 38;
                    break;
                case 17:
                    action = 17;
                    break;
                default:
                    action = key;
            }
        }
        super.keyClick(bl, action);
        if (bl) {
            switch (action) {
                case 37:
                    this.right = false;
                    this.down = false;
                    this.up = false;
                    this.left = true;
                    this.direction = Direction.left;
                    keySet.add(action);
                    moveStatus = true;
                    break;
                case 38:
                    this.right = false;
                    this.down = false;
                    this.left = false;
                    this.up = true;
                    this.direction = Direction.up;
                    keySet.add(action);
                    moveStatus = true;
                    break;
                case 39:
                    this.up = false;
                    this.down = false;
                    this.left = false;
                    this.right = true;
                    this.direction = Direction.right;
                    keySet.add(action);
                    moveStatus = true;
                    break;
                case 40:
                    this.left = false;
                    this.right = false;
                    this.up = false;
                    this.down = true;
                    this.direction = Direction.down;
                    keySet.add(action);
                    moveStatus = true;
                    break;
                case 17:
                    this.attackStatus = true;
                    break;
                default:
                    break;
            }
        } else {
            switch (action) {
                case 37:
                    this.left = false;
                    keySet.remove(action);
                    break;
                case 38:
                    this.up = false;
                    keySet.remove(action);
                    break;
                case 39:
                    this.right = false;
                    keySet.remove(action);
                    break;
                case 40:
                    this.down = false;
                    keySet.remove(action);
                    break;
                case 17:
                    this.attackStatus = false;
                    break;
                default:
                    break;
            }
        }
        if (keySet.isEmpty()) {
            moveStatus = false;
        }


    }

    /**
     * 覆盖父类的移动方法
     */
    @Override
    public void move() {
//        if(isStuckStatus() || isBoomStuckStatus() || moveStatus==false){
//            return;
//        }
        if (this.left && this.getX() - speed >= 0 && impactDirection != direction) {
            this.imgY = 1;
            this.setX(this.getX() - speed);

        }
        if (this.right && this.getX() + speed <= 448 && impactDirection != direction) {
            this.imgY = 2;
            this.setX(this.getX() + speed);
        }
        if (this.up && this.getY() - speed >= 0 && impactDirection != direction) {
            this.imgY = 3;
            this.setY(this.getY() - speed);
        }
        if (this.down && this.getY() + speed <= 384 && impactDirection != direction) {
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
        if (gameTime - time > 10) {
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
     * 坐标位置:发者的左上角
     */
    @Override
    public void add() {
        //如果不是发射状态 直接return
        if (!this.attackStatus || boomNum / 2 == 0) {
            return;
        }
        this.attackStatus = false;
        ElementObj elementObj = new Boom().createElement(this.getX() + "," + this.getY() + "," + this.boomLength);
        ModelManager.getManager().addElement(elementObj, GameElement.PLAYFILE, ((this.getX() + 16) / 32), (this.getY() + 24) / 32);
        boomNum -= 2;
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(4000);
                boomNum += 2;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

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

    @Override
    public ElementObj createElement(String str) {
        String[] strs = str.split(",");
        this.setX(new Integer(strs[0]));
        this.setY(new Integer(strs[1]));
        ImageIcon icon = GameLoad.playIconMap.get("play2");
        this.isEnemy = Boolean.valueOf(strs[3]);
        this.setW(22);
        this.setH(12);
        this.setIcon(icon);
        this.direction = Direction.up;
        this.imgX = 0;
        this.imgY = 1;
        this.speed = 1;
        this.time = 0;
        this.boomNum = 2;
        this.boomLength = 1;
        keySet = new HashSet<>();
        poingLeftUp = new Point();
        pointRightDown = new Point();
        poingLeftUp.setLocation(27, 42);
        pointRightDown.setLocation(72, 100);
        this.impactDirection = Direction.none;
        this.setReverseStatus(false);
        this.setSuperCard(0);
        this.setStuckStatus(false);
        this.setBoomStuckStatus(false);
        return this;
    }

    @Override
    public Rectangle getRectangel() {
        return new Rectangle(this.getX() + 11, this.getY() + 14, 10, 19);
    }

    public void onImpact(ElementObj elementObj) {
//        switch (direction){
//            case up: this.setY(this.getY()+1);break;
//            case down: this.setY(this.getY()-1);break;
//            case right: this.setX(this.getX()-1);break;
//            case left: this.setX(this.getX()+1);break;
//        }



        Rectangle target = elementObj.getRectangel();
        Rectangle rectangle = new Rectangle(this.getX() + 10, this.getY() + 14, 10, 19);
        if (!rectangle.intersects(target)) {
            this.setX(this.getX() - 1);
        }
        rectangle = new Rectangle(this.getX() + 12, this.getY() + 14, 10, 19);
        if (!rectangle.intersects(target)) {
            this.setX(this.getX() + 1);
        }
        rectangle = new Rectangle(this.getX() + 11, this.getY() + 13, 10, 19);
        if (!rectangle.intersects(target)) {
            this.setY(this.getY() - 1);
        }
        rectangle = new Rectangle(this.getX() + 11, this.getY() + 15, 10, 19);
        if (!rectangle.intersects(target)) {
            this.setY(this.getY() + 1);
        }

    }

    @Override
    public boolean impact(ElementObj elementObj) {
        return super.impact(elementObj);
    }

    @Override
    public void setLiveStatus(boolean liveStatus) {
        super.setLiveStatus(liveStatus);
        if (!liveStatus) {
            ModelManager.getManager().getPlayers().remove(this);
        }
    }


    public int getBoomNum() {
        return boomNum;
    }

    public void setBoomNum(int boomNum) {
        this.boomNum = boomNum;
    }

    public int getBoomLength() {
        return boomLength;
    }

    public void setBoomLength(int boomLength) {
        this.boomLength = boomLength;
    }

    public boolean isReverseStatus() {
        return reverseStatus;
    }

    public void setReverseStatus(boolean reverseStatus) {
        if (keySet.size() > 0) {
            keySet.remove(38);
            moveStatus = false;
        }
        this.reverseStatus = reverseStatus;
    }

    public int getSuperCard() {
        return superCard;
    }

    public void setSuperCard(int superCard) {
        this.superCard = superCard;
    }

    public boolean isStuckStatus() {
        return stuckStatus;
    }

    public void setStuckStatus(boolean stuckStatus) {
        this.stuckStatus = stuckStatus;
        if (stuckStatus) {
            this.speed = 0;
        } else {
            this.speed = 1;
        }
    }

    public boolean isBoomStuckStatus() {
        return boomStuckStatus;
    }

    public void setBoomStuckStatus(boolean boomStuckStatus) {
        if (boomStuckStatus) {
            if (superCard == 0) {
                this.speed = 0;
                this.boomStuckStatus = true;
                this.setIcon(GameLoad.playIconMap.get("boomAll"));
                this.moveStatus = true;
                poingLeftUp.setLocation(19,23);
                pointRightDown.setLocation(80,100);
            } else superCard--;

        } else {
            this.speed = 1;
            this.boomStuckStatus = false;
            poingLeftUp.setLocation(27, 42);
            pointRightDown.setLocation(72, 100);
            if (isEnemy) {
                this.setIcon(GameLoad.playIconMap.get("play2"));
            } else {
                this.setIcon(GameLoad.playIconMap.get("play"));
            }
        }


    }
}

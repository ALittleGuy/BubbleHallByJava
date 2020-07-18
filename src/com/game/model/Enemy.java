package com.game.model;

import com.game.manager.GameLoad;
import com.game.model.Enum.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Enemy extends ElementObj {

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private Direction direction = Direction.up;
    private int speed;


    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);

    }

    public Enemy(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
        this.speed = 1;
    }

    public Enemy() {
    }

    public ElementObj createElement(String str){
        Random random = new Random();
        int x = random.nextInt(900);
        int y = random.nextInt(600);
        this.setX(x);
        this.setY(y);
        this.setH(50);
        this.setW(50);
        this.setIcon(new ImageIcon("src/res/image/image/tank/bot/bot_up.png"));
        return this;
    }

    public void move() {
        int x = this.getX();
        if (x > 600) {
            this.direction = Direction.left;
        }
        if (x < 300) {
            this.direction = Direction.right;
        }

        switch (direction) {
            case up:
            case down:
                direction = Direction.right;
                this.setX(x + speed);
                break;
            case left:
                this.setX(x - speed);
                break;
            case right:
                this.setX(x + speed);
                break;
        }


    }

    @Override
    public void updateImage(long gameTime){
        this.setIcon(GameLoad.enemyIconMap.get(direction));

    }
}

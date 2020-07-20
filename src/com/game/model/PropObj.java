package com.game.model;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.Enum.GameProps;

import javax.swing.*;
import java.awt.*;

public class PropObj extends ElementObj {

    private GameProps gameProps;
    private int imgX;
    private long localGameTime;

    @Override
    public void showElement(Graphics graphics) {
        graphics.drawImage(this.getIcon().getImage(),
                this.getX() * this.getW()+5, this.getY() * this.getH() ,
                this.getX() * this.getW()-5 + this.getW(),
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

    @Override
    public void setLiveStatus(boolean liveStatus) {
        super.setLiveStatus(liveStatus);
        if (!isLiveStatus()) {
            System.out.println(this.getX() + "," + this.getY());

            ModelManager.getManager().remove(GameElement.PROP, this.getX(), this.getY());
        }
    }

    @Override
    public Rectangle getRectangel() {
        return new Rectangle(this.getX() * 32, this.getY() * 32, this.getW(), this.getH());
    }

    @Override
    public void onImpact(ElementObj elementObj) {
        java.util.List<ElementObj> plays = ModelManager.getManager().getPlayers();
        Play play = (Play) elementObj;
        switch (gameProps) {
            case BOOM_NUM_INCREASE:
                play.setBoomNum(play.getBoomNum() + 1);
                break;
            case BLUE_MEDICINE:
                play.setBoomLength(play.getBoomLength() + 1);
                break;
            case PURPLE_GHOST:
                purpleGhost(play);
                break;
            case RED_GHOST:
                for (ElementObj obj : plays) {
                    Play temp = (Play) obj;
                    if (temp != play) {
                        purpleGhost(temp);
                    }
                }
                break;
            case SUPER_CARD:
                play.setSuperCard(play.getSuperCard() + 1);
                break;
            case CONTROLLER:
                for (ElementObj obj : plays) {
                    Play temp = (Play) obj;
                    if (temp != play) {
                        controller(temp);
                    }
                }
                break;
            case PURPLE_MEDICINE:
                play.setReverseStatus(false);
                break;
            case WATER_MINE:
                for (ElementObj obj : plays) {
                    Play temp = (Play) obj;
                    if (temp != play) {
                        waterMime(temp);
                    }
                }
                break;

        }
    }

    public void purpleGhost(Play play) {
        play.setReverseStatus(true);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(20000);
                play.setReverseStatus(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void controller(Play play) {
        play.setStuckStatus(true);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                play.setStuckStatus(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void waterMime(Play play) {
        play.setBoomStuckStatus(true);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                play.setBoomStuckStatus(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

}

package com.game.controller;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.ElementObj;
import com.game.model.Enemy;
import com.game.model.Play;
import com.sun.org.apache.xpath.internal.operations.Mod;
import sun.font.SunFontManager;
import sun.management.VMOptionCompositeData;

import javax.jws.WebParam;
import javax.swing.*;
import javax.xml.bind.Element;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 游戏主线程
 * 用于控制游戏加载,游戏关卡,游戏运行时候的自动化
 * 游戏判定,游戏地图切换,资源释放和重新读取
 * <p>
 * 一般建议使用接口
 *
 * @author prh
 */
public class GameThread implements Runnable {

    private ModelManager modelManager;
    private long gametime = 0;
    public GameThread() {
        modelManager = ModelManager.getManager();
    }

    /**
     * 游戏的run方法 主线程
     */
    @Override
    public void run() {

        GameLoad.MapLoad(1);
        while (true) {
//      游戏开始前 加载游戏资源或者场景资源
            gameLoad();

//      游戏进行时 游戏过程中
            gameRun();
//      游戏关卡结束 游戏资源回收
            gameOver();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 游戏切换关卡
     */
    private void gameOver() {
    }


    /**
     * 游戏进行时
     * 1.自动化玩家的移动
     * 2.碰撞
     * 3.死亡
     * 4.新元素的增加
     * 5......
     * <p>
     * 1.主角的移动
     */

    private void gameRun() {
        while (true) { //预留扩展 控制关卡的暂停等等

            Map<GameElement, List<ElementObj>> all = modelManager.getGameElements();
            List<ElementObj> map = modelManager.getElementsByKey(GameElement.MAP);
            List<ElementObj> enemy = modelManager.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> field = modelManager.getElementsByKey(GameElement.PLAYFILE);

            elementUpdate(all);
            ElementPK(enemy , field);
            ElementPK(map , field);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ElementPK(List<ElementObj> listA , List<ElementObj> listB) {

        for (int i = 0; i <listA.size() ; i++) {

            ElementObj enemy = listA.get(i);

            for (int j = 0; j <listB.size(); j++) {
                ElementObj playfile = listB.get(j);
                if(enemy.impact(playfile)){
                    enemy.setLiveStatus(false);
                    playfile.setLiveStatus(false);
                    break;
                }
            }

        }
    }


    //游戏元素自动化方法
    private void elementUpdate(Map<GameElement, List<ElementObj>>  all){
        for (GameElement value : GameElement.values()) {
            List<ElementObj> list = all.get(value);
            for (int i = list.size()-1; i >=0; i--) {
                ElementObj elementObj = list.get(i);
                if(!elementObj.isLiveStatus()){
                    list.remove(i);

                    //开启一个死亡动画


                    continue;
                }
                elementObj.model(gametime);
            }
        }
        gametime++;
        
    }

    /**
     * 游戏的加载
     */
    private void gameLoad() {
        //图片导入
        ImageIcon icon = new ImageIcon("src/res/image/play1/WeChat Image_20200213234835.jpg");
        ImageIcon enemyIcon = new ImageIcon("src/res/image/image/tank/bot/bot_up.png");
        ElementObj enemyObj = new Enemy(0, 0, 50, 50, enemyIcon);
        ElementObj elementObj = new Play(100, 100, 50, 50, icon);
        for (int i = 0; i <10 ; i++) {
            modelManager.addElement(new Enemy().createElement(""),GameElement.ENEMY);
        }
        modelManager.getElementsByKey(GameElement.PLAY);
        modelManager.addElement(elementObj, GameElement.PLAY);
        modelManager.addElement(enemyObj , GameElement.ENEMY);
    }
}


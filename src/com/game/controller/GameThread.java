package com.game.controller;

import com.game.manager.GameElement;
import com.game.manager.GameLoad;
import com.game.manager.ModelManager;
import com.game.model.ElementObj;

import java.util.List;
import java.util.Map;

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
            Map<GameElement, ElementObj[][]> all = modelManager.getGameElements();
//            List<ElementObj> map = modelManager.getElementsByKey(GameElement.MAP);
//            List<ElementObj> enemy = modelManager.getElementsByKey(GameElement.ENEMY);
//            List<ElementObj> field = modelManager.getElementsByKey(GameElement.PLAYFILE);
            elementUpdate(all);
//            ElementPK(enemy , field);
//            ElementPK(map , field);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ElementPK(List<ElementObj> listA , List<ElementObj> listB) {
//        for (int i = 0; i <listA.size() ; i++) {
//            for (int j = 0; j <listB.size(); j++) {
//                ElementObj playfile = listB.get(j);
//                if(enemy.impact(playfile)){
//                    enemy.setLiveStatus(false);
//                    playfile.setLiveStatus(false);
//                    break;
//                }
//            }
//        }
    }


    //游戏元素自动化方法
    private void elementUpdate(Map<GameElement, ElementObj[][] >  all){
        for (GameElement value : GameElement.values()) {
            ElementObj[][] lists = all.get(value);
            for (int i = 0; i < lists.length; i++) {
                for (int j = 0; j <lists[i].length ; j++) {
                    if(lists[i][j]==null){
                        continue;
                    }
                    if (!lists[i][j].isLiveStatus()){
                        modelManager.remove(value,i,j);
                    }
                    lists[i][j].model(gametime);
                }
            }
        }
        gametime++;
    }

    /**
     * 游戏的加载
     */
    private void gameLoad() {
        GameLoad.loadImage(3);
        GameLoad.loadMap(4);
        GameLoad.loadPlay(1);

    }
}

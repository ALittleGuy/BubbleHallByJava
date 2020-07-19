package com.game.view;

import com.game.manager.GameElement;
import com.game.manager.ModelManager;
import com.game.model.ElementObj;
import com.game.model.Play;
import com.sun.xml.internal.bind.v2.model.core.EnumLeafInfo;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 游戏主面板
 * 多线程刷新
 * @author prh  <p>功能说明:进行元素的显示和界面的刷新<p/>
 */
public class GameMainJanel extends JPanel implements Runnable{
    //联动管理器
    private ModelManager modelManager ;

    /**
     * Instantiates a new Game main janel.
     */
    public GameMainJanel(){
        init();
    }

    private void init() {
        modelManager = ModelManager.getManager();
    }





    public void paint(Graphics graphics){
        super.paint(graphics);
        Map<GameElement , ElementObj[][]> all = modelManager.getGameElements();
        List<ElementObj> players = modelManager.getPlayers();
        for (GameElement value : GameElement.values()) {
            ElementObj[][] elementObjs = all.get(value);
            for (int i = 0; i <elementObjs.length ; i++) {
                for (int j = 0; j <elementObjs[i].length ; j++) {
                    ElementObj elementObj = elementObjs[i][j];
                    if(elementObj!=null && elementObj.isLiveStatus()){
                        elementObj.showElement(graphics);
                    }
                }

            }
        }
        for (ElementObj player : players) {
            player.showElement(graphics);
        }



    }

    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

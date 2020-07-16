package com.game.view;

import com.game.manager.GameElement;
import com.game.manager.ModelManager;
import com.game.model.ElementObj;
import com.game.model.Play;

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
        load();
    }

    private void init() {
        modelManager = ModelManager.getManager();
    }



    public void load(){
//        ImageIcon icon = new ImageIcon("src/res/image/play1/WeChat Image_20200213234835.jpg");
//        System.out.println(icon.toString());
//        ElementObj elementObj = new Play(100,100,50,50 ,icon);
//        modelManager.getElementsByKey(GameElement.PLAY);
//        modelManager.addElement(elementObj , GameElement.PLAY);
    }

    public void paint(Graphics graphics){
        super.paint(graphics);
        Map<GameElement , List<ElementObj>> all = modelManager.getGameElements();

        for (GameElement value : GameElement.values()) {
            List<ElementObj> list = all.get(value);
            for (int i = 0; i <list.size() ; i++) {
                ElementObj elementObj = list.get(i);
                elementObj.showElement(graphics);
            }
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

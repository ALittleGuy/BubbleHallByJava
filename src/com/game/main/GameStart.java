package com.game.main;

import com.game.controller.GameListener;
import com.game.controller.GameThread;
import com.game.view.GameJFrame;
import com.game.view.GameMainJanel;
import sun.awt.CharsetString;

import java.util.stream.IntStream;

/**
 * <p>程序的唯一入口<p/>
 */
public class GameStart {
    public static void main(String[] args) {
        GameJFrame gameJFrame = new GameJFrame();
        GameMainJanel gameMainJanel = new GameMainJanel();

        //实例化监听
        GameListener gameListener = new GameListener();

        gameJFrame.setKeyListener(gameListener);
        gameJFrame.setjPanel(gameMainJanel);
        Thread mainThread = new Thread(new GameThread());
        gameJFrame.setMainThread(mainThread);
        gameJFrame.start();


    }

}

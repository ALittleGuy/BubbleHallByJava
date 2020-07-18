package com.game.controller;

import com.game.manager.GameElement;
import com.game.manager.ModelManager;
import com.game.model.ElementObj;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * The type Game listener.
 *
 * @author prh
 */
public class GameListener implements KeyListener, MouseListener {

    private ModelManager modelManager = ModelManager.getManager();

    /**
     * 键盘监听
     *
     * @param keyEvent
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    HashSet<Integer> hashSet = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        if (hashSet.contains(key)) {
            return;
        }
        hashSet.add(key);


        ElementObj[][] player =  modelManager.getElementsByKey(GameElement.PLAY);

        for (int i = 0; i < player.length ; i++) {
            for (int j = 0; j < player[i].length ; j++) {
                if(player[i][j]!=null){
                    player[i][j].keyClick(true, keyEvent.getKeyCode());
                }
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        if (!hashSet.contains(keyEvent.getKeyCode())) {
            return;
        }
        hashSet.remove(keyEvent.getKeyCode());
        ElementObj[][] player =  modelManager.getElementsByKey(GameElement.PLAY);
        for (int i = 0; i < player.length ; i++) {
            for (int j = 0; j < player[i].length ; j++) {
                if(player[i][j]!=null){
                    player[i][j].keyClick(false, keyEvent.getKeyCode());
                }
            }
        }
    }


    /**
     * 鼠标监听
     *
     * @param mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

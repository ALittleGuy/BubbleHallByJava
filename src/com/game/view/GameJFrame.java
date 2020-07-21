package com.game.view;


import com.sun.org.apache.xml.internal.resolver.helpers.BootstrapResolver;
import com.sun.xml.internal.stream.dtd.DTDGrammarUtil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * <p>游戏窗体 主要实现:关闭 , 显示 , 放大 , 缩小<p/>
 *
 * @author prh
 * <p>功能说明:需要嵌入面吧板,启动主线程<p/>
 * <p>窗体说明 swing awt(记录用户上次使用软件的窗体样式)<p/>
 * <p>分析
 * 1.面板绑定到窗体
 * 2.监听绑定
 * 3.游戏主线程启动
 * 4.显示窗体
 * <p/>
 */
public class GameJFrame extends JFrame {
    private static int GameX = 610;
    private static int GameY = 544;

    private JPanel jPanel = null;  //正在显示的面板
    private KeyListener keyListener = null; //键盘监听
    private MouseListener mouseMoveListener = null; //鼠标监听
    private MouseMotionListener mouseMotionListener = null;//鼠标监听
    private Thread mainThread = null; //游戏主线程


    public GameJFrame() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setSize(GameX, GameY);
        this.setTitle("J");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //设置退出时关闭窗体
        this.setLocationRelativeTo(null);
    }

    /**
     * 窗体布局: (扩展内容)
     */
    public void addButton(){
//        this.setLayout(manager);
    }

    /**
     * 启动方法
     */
    public void start(){
        if(jPanel != null){
            this.add(jPanel, BorderLayout.CENTER);
            JPanel jPanelEast = new PartJpanel("src/res/image/Pictures/right_sight.png",120,416);
            JPanel jPanelSouth = new PartJpanel("src/res/image/Pictures/menuBar.png",GameX ,48);
            JPanel jPanelNorth = new PartJpanel("src/res/image/Pictures/menuBar.png",GameX ,48);
            this.add(jPanelEast, BorderLayout.EAST );
            this.add(jPanelSouth,BorderLayout.SOUTH);
            this.add(jPanelNorth,BorderLayout.NORTH);
        }

        if(keyListener!=null){
            this.addKeyListener(keyListener);
        }
        if(mainThread != null){
            mainThread.start();
        }

        //界面刷新
        this.setVisible(true); //显示界面

        if(this.jPanel instanceof Runnable){
            new Thread((Runnable) this.jPanel).start();
        }
    }

    /**
     * set注入
     * @param jPanel
     */
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMoveListener(MouseListener mouseMoveListener) {
        this.mouseMoveListener = mouseMoveListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMainThread(Thread mainThread) {
        this.mainThread = mainThread;
    }
}

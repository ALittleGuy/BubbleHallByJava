package com.game.manager;

import com.game.model.Direction;
import com.game.model.ElementObj;
import com.game.model.MapObj;
import com.game.model.Play;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.sun.xml.internal.ws.api.client.WSPortInfo;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 工具类
 */
public class GameLoad {

    private static ModelManager modelManager = ModelManager.getManager();
    public static Map<Direction, ImageIcon> playIconMap = new HashMap<>();

//    static {
//        imageIconMap = new HashMap<>();
//        imageIconMap.put(Direction.up, new ImageIcon("src/res/image/image/tank/play1/player1_up.png"));
//        imageIconMap.put(Direction.down, new ImageIcon("src/res/image/image/tank/play1/player1_down.png"));
//        imageIconMap.put(Direction.right, new ImageIcon("src/res/image/image/tank/play1/player1_right.png"));
//        imageIconMap.put(Direction.left, new ImageIcon("src/res/image/image/tank/play1/player1_left.png"));
//    }

    public static Map<Direction, ImageIcon> enemyIconMap;

    static {
        enemyIconMap = new HashMap<>();
        enemyIconMap.put(Direction.left, new ImageIcon("src/res/image/image/tank/bot/bot_left.png"));
        enemyIconMap.put(Direction.right, new ImageIcon("src/res/image/image/tank/bot/bot_right.png"));
        enemyIconMap.put(Direction.up, new ImageIcon("src/res/image/image/tank/bot/bot_up.png"));
        enemyIconMap.put(Direction.down, new ImageIcon("src/res/image/image/tank/bot/bot_down.png"));
    }


    private static Properties properties = new Properties();

    public static void MapLoad(int mapId) {

        properties.clear();
        //文件路径
        String mapName = mapId + ".map";

        //用IO流读取文件对象
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);

        if (maps == null) {
            System.out.println("文件读取错误");
        }
        try {
            properties.load(maps);

            //可以直接动态的获取所有的key
            Enumeration<?> names = properties.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                String[] arrs = properties.getProperty(key).split(";");
                for (int i = 0; i < arrs.length; i++) {
                    MapObj mapObj = (MapObj) new MapObj().createElement(key + "," + arrs[i]);
                    modelManager = ModelManager.getManager();
                    modelManager.addElement(mapObj, GameElement.MAP);
                    System.out.println(mapObj.toString());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MapLoad(5);
    }


    /**
     *
     */
    public static void loadImage(int mapId) {

        String texturl = "GameData.config";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);

//        playIconMap存取玩家图片
        try {
            properties.clear();
            properties.load(texts);
            properties.keys();
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                String url = properties.getProperty(o.toString());
                System.out.println(new ImageIcon(url).toString());
                playIconMap.put(Direction.getDirectionByname(o.toString()), new ImageIcon(url));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 扩展:使用配置文件来实例化对象,通
     */
    private static Map<String , Class<?>> objMap = new HashMap<>();
    public static void loadObj(){
        String texturl = "obj.config";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts =classLoader.getResourceAsStream(texturl);
        properties.clear();
        try {
            properties.load(texts);
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                System.out.println(o.toString());
                String classUrl = properties.getProperty(o.toString());
                Class<?> forname = Class.forName(classUrl);
                objMap.put(o.toString(),forname);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    /**
     * 加载玩家
     * @param i
     */
    public static void loadPlay(int i) {
        loadObj();
        String playStr = "500,500,up";
//        Play play = (Play) new Play().createElement(playStr);
        Class<?> classPlay = objMap.get("play");
        Object newInstance = null;
        try {
            newInstance = classPlay.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ElementObj play = null;

        if(newInstance instanceof ElementObj){
            play = ((ElementObj) newInstance).createElement(playStr);
        }else {
            System.out.println("error");
        }

        modelManager.addElement(play , GameElement.PLAY );

    }
}

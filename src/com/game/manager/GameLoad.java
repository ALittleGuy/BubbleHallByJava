package com.game.manager;

import com.game.model.Direction;
import com.game.model.MapObj;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 工具类
 */
public class GameLoad {

    public static Map<Direction, ImageIcon> imageIconMap;

    static {
        imageIconMap = new HashMap<>();
        imageIconMap.put(Direction.up, new ImageIcon("src/res/image/image/tank/play1/player1_up.png"));
        imageIconMap.put(Direction.down, new ImageIcon("src/res/image/image/tank/play1/player1_down.png"));
        imageIconMap.put(Direction.right, new ImageIcon("src/res/image/image/tank/play1/player1_right.png"));
        imageIconMap.put(Direction.left, new ImageIcon("src/res/image/image/tank/play1/player1_left.png"));
    }

    public static Map<Direction, ImageIcon> enemyIconMap;

    static {
        enemyIconMap = new HashMap<>();
        enemyIconMap.put(Direction.left, new ImageIcon("src/res/image/image/tank/bot/bot_left.png"));
        enemyIconMap.put(Direction.right, new ImageIcon("src/res/image/image/tank/bot/bot_right.png"));
        enemyIconMap.put(Direction.up, new ImageIcon("src/res/image/image/tank/bot/bot_up.png"));
        enemyIconMap.put(Direction.down, new ImageIcon("src/res/image/image/tank/bot/bot_down.png"));
    }


    private static Properties properties = new Properties();

    public static void MapLoad(int mapId){
        //文件路径
        String mapName = mapId+".map";

        //用IO流读取文件对象
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);

        if(maps == null){
            System.out.println("文件读取错误");
        }
        try {
            properties.load(maps);

            //可以直接动态的获取所有的key
            Enumeration<?> names = properties.propertyNames();
            while (names.hasMoreElements()){
                String key = names.nextElement().toString();
                String[] arrs = properties.getProperty(key).split(";");
                for (int i = 0; i <arrs.length ; i++) {
                    MapObj mapObj = (MapObj) new MapObj().createElement(key+","+arrs[i]);
                    ModelManager modelManager =ModelManager.getManager();
                    modelManager.addElement(mapObj , GameElement.MAP);
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

    public static void loadImage(){

    }

}

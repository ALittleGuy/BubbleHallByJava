package com.game.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.game.model.ElementObj;
import com.game.model.Enum.Direction;
import com.game.model.Enum.GameProps;
import com.game.model.FloorObj;
import com.game.model.MapObj;
import com.game.model.PropObj;

import javax.swing.*;
import javax.xml.stream.events.StartDocument;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 工具类
 */
public class GameLoad {

    public static Map<String, ImageIcon> playIconMap = new HashMap<>();
    public static Map<Direction, ImageIcon> enemyIconMap;
    public static Map<String, ImageIcon> mapIconMap = new HashMap<>();
    private static ModelManager modelManager = ModelManager.getManager();
    private static Properties properties = new Properties();
//    public static void main(String[] args) {
//        loadMap(1);
//    }

    public static void loadMap(int mapId) {
        String floorFile = "map_config/floor.json";
        String boxFile   = "map_config/box.json";
        loadByFileName(floorFile  , "com.game.model.FloorObj" , GameElement.FLOOR); //加载地板
        loadByFileName(boxFile , "com.game.model.BoxObj", GameElement.MAP);
        ElementObj propObj = new PropObj().createElement("8,8,"+GameProps.WATER_MINE.name());
        ElementObj propObj1 = new PropObj().createElement("3,3,"+GameProps.RED_GHOST.name());
        ElementObj temp = new PropObj().createElement("1,1,"+GameProps.CONTROLLER.name());
        modelManager.addElement(propObj,GameElement.PROP , 8,8);
        modelManager.addElement(temp,GameElement.PROP , 1,1);
        modelManager.addElement(propObj1 , GameElement.PROP , 3,3);
    }


    /**
     *加载图片
     */
    public static void loadImage(int mapId) {
        String texturl = "GameData.config";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        try {
            properties.clear();
            properties.load(texts);
            properties.keys();
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                String url = properties.getProperty(o.toString());
                playIconMap.put(o.toString(), new ImageIcon(url));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 扩展:使用配置文件来实例化对象,通
     * 加载玩家配置
     */
    private static Map<String, Class<?>> objMap = new HashMap<>();

    public static void loadObj() {
        String texturl = "obj.config";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
        properties.clear();
        try {
            properties.load(texts);
            Set<Object> set = properties.keySet();
            for (Object o : set) {
                System.out.println(o.toString());
                String classUrl = properties.getProperty(o.toString());
                Class<?> forname = Class.forName(classUrl);
                objMap.put(o.toString(), forname);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        }
    }

    /**
     * 加载玩家
     *
     * @param i
     */
    public static void loadPlay(int i) {
        loadObj();
        String playStr = "96,128,play";
        String enemyStr = "128,96,play";

        Class<?> classPlay = objMap.get("play");
        Class classEnemy = objMap.get("enemy");
        Object newInstance = null;
        Object newInstance1 = null;
        try {
            newInstance = classPlay.newInstance();
            newInstance1 = classEnemy.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ElementObj play = null;
        ElementObj enemy = null;
        if (newInstance instanceof ElementObj) {
            play = ((ElementObj) newInstance).createElement(playStr);

        } else {
            System.out.println("error");
        }

        if (newInstance1 instanceof ElementObj) {
            enemy = ((ElementObj) newInstance).createElement(enemyStr);

        } else {
            System.out.println("error");
        }
        modelManager.addPlayer(play);
        modelManager.addPlayer(enemy);
    }






    private static void loadByFileName(String fileName , String element ,  GameElement gameElement){

        Class targetElement = null;
        try {
            targetElement = Class.forName(element);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream mapInputStream = classLoader.getResourceAsStream(fileName);
        BufferedReader mapBufferedReader  = new BufferedReader(new InputStreamReader(mapInputStream, StandardCharsets.UTF_8));
        if (mapInputStream == null) {
            System.out.println("文件读取错误");
        }
        StringBuilder content = new StringBuilder("");
        String line;
        JSONArray jsonArray = null;
        while (true) {
            try {
                if (!(null!=(line = mapBufferedReader.readLine()))) break;
                content.append(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonArray = JSON.parseArray(content.toString());

        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;
            int x = (int) jsonObject.get("x");
            int y = (int) jsonObject.get("y");
            String type = "";
            if(gameElement == GameElement.MAP) {
                 type = (String) jsonObject.get("type");
            }
            ElementObj elementObj = null;
            try {
                elementObj = (ElementObj) targetElement.newInstance();
                elementObj = elementObj.createElement(x+","+y+","+type);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            modelManager.addElement(elementObj , gameElement , x , y);
        }


        try {
            if(mapBufferedReader!=null){
                mapBufferedReader.close();}
            if(mapInputStream!=null){
                mapInputStream.close();}
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

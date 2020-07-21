package com.game.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.game.model.*;
import com.game.model.Enum.Direction;
import com.game.model.Enum.GameProps;
import com.game.tools.Pair;

import javax.swing.*;
import javax.xml.stream.events.StartDocument;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.SelectableChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
        loadProps();
//        ElementObj propObj = new PropObj().createElement("8,8,"+GameProps.WATER_MINE.name());
//        ElementObj propObj1 = new PropObj().createElement("3,3,"+GameProps.RED_GHOST.name());

//        modelManager.addElement(propObj,GameElement.PROP , 8,8);
//        modelManager.addElement(temp,GameElement.PROP , 1,1);
//        modelManager.addElement(propObj1 , GameElement.PROP , 3,3);
//


    }

    private static void loadProps() {
        ElementObj[][] elementObj = modelManager.getElementsByKey(GameElement.MAP);
        double sum = 0;
        List<Pair> locationSet = new ArrayList<>();
        for (ElementObj[] elementObjs : elementObj) {
            for (ElementObj obj : elementObjs) {
                BoxObj temp = (BoxObj) obj;
                if(obj!=null&&temp.isBreakable()){
                    sum = sum+1;
                    locationSet.add(new Pair<>(obj.getX() , obj.getY()));
                }

            }
        }
        System.out.println(sum);
        sum*=0.8;
        GameProps gameProps[] = GameProps.values();
        for (GameProps gameProp : gameProps) {
            int boud = (int) (sum*gameProp.getAppearRate() -1);
            if(boud==0&&sum>0){
                boud =1;
            }
            sum-=boud;
            for (int i = 0; i < boud; i++) {
                int a = locationSet.size();
                if(a==0){
                    break;
                }
                if(a != 0){
                    a = new Random().nextInt(a);
                }
                Pair<Integer,Integer> location = locationSet.get(a);
                locationSet.remove(a);
                ElementObj temp = new PropObj().createElement(location.getKey()+","+location.getValue()+","+gameProp.name());
//                System.out.println(location.getKey() +","+location.getValue());
                modelManager.addElement(temp , GameElement.PROP , location.getKey() , location.getValue() );
            }
        }

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
        String play1Str = "0,0,play,false";
        String play2Str = "448,352,play2,true";
        Class<?> classPlay = objMap.get("play");
        Object  play1= null;
        Object play2 =  null;
        try {
            play1 = classPlay.newInstance();
            play2 = classPlay.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ElementObj play1Obj = null;
        ElementObj play2Obj = null;
        if (play1 instanceof ElementObj) {
            play1Obj = ((ElementObj) play1).createElement(play1Str);
        } else {
            System.out.println("error");
        }

        if(play2 instanceof ElementObj){
            play2Obj =  ((ElementObj)play2).createElement(play2Str);
        }else {
            System.out.println("error");
        }
        modelManager.addPlayer(play1Obj);
        modelManager.addPlayer(play2Obj);
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
            String type = (String) jsonObject.get("type");
            ElementObj elementObj = null;
            try {
                elementObj = (ElementObj) targetElement.newInstance();
                elementObj = elementObj.createElement(x+","+y+","+type);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(type.equals("GRASS")){
                modelManager.addElement(elementObj,GameElement.GRASS , x, y);
            }else {
                modelManager.addElement(elementObj , gameElement , x , y);
            }
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

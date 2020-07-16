package com.game.manager;

import com.game.model.ElementObj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/*
 * @author prh
 * <p>设计模式:单例设计 本类是元素管理器,专门管理所有元素,同事,提供方法给予视图和控制器获取数据</p>
 */
public  class  ModelManager {

    /**
     * @value 全局实例
     */
    private static ModelManager MM = null;


    /**
     * 存储所有元素 {@value}
     */
    private Map<GameElement , List<ElementObj>> gameElements;


    /**
     * 私有化构造函数
     */
    private ModelManager(){
        init();
    }


    /**
     *  <p>添加元素</>
     */
    public void addElement(ElementObj obj , GameElement ge){
        List<ElementObj> list = gameElements.get(ge);
        list.add(obj);
    }


    /**
     * 根据游戏元素获取对应的元素列表
     * @param ge 游戏元素
     * @return
     */
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }

    /**
     *
     * @return 返回单例
     */
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }

    public static synchronized ModelManager getManager(){
        if(MM == null){
            MM = new ModelManager();
        }
        return MM;
    }

    /**
     * 为将来的功能扩展,重写init方法准备的
     */
    public void init(){
        gameElements = new HashMap<>();

//        将每种元素集合都放入到map中
        for (GameElement value : GameElement.values()){
            gameElements.put(value , new ArrayList<>());
        }
    }


}

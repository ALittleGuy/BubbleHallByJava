package com.game.model.Enum;

import java.util.HashMap;
import java.util.Map;

public enum GameProps {
    /**
     * 增加泡泡数量 ， 每获得两个增加一个
     */
    BOOM_NUM_INCREASE(0.2),

    /**
     * 蓝色药瓶，增加泡泡威力（爆炸长度）
     */
    BLUE_MEDICINE(0.2),

    /**
     * 紫色药瓶，消除反向行走的debuff
     */
    PURPLE_MEDICINE(0.1),

    /**
     * 紫鬼头，玩家获得反向行走的debuff
     */
    PURPLE_GHOST(0.1),

    /**
     * 红鬼头，对手获得反向行走的debuff
     */
    RED_GHOST(0.1),

    /**
     * 遥控器，使对手停止50帧
     */
    CONTROLLER(0.1),

    /**
     * 超人卡 ，获得一次地域魔水雷的机会
     */
    SUPER_CARD(0.1),

    /**
     * 水雷，使得对手被泡泡包围
     */
    WATER_MINE(0.1);

    private double appearRate;

    GameProps(double appearRate) {
        this.appearRate = appearRate;
    }

    public double getAppearRate() {
        return appearRate;
    }
}

package com.game.test;

import java.util.ArrayList;
import java.util.List;

public class listText {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        list.add(5,1);
        System.out.println(list.toString());
        System.out.println(list.get(5));
    }
}

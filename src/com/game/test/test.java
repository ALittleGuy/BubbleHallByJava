package com.game.test;

import sun.rmi.server.InactiveGroupException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class test {


    static BinaryOperator<Integer> temp = Integer::sum;

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        IntStream.range(1,10).forEach(list::add);

        System.out.println("ans:");
        eval(list , n->n%2==0);
    }

    public static void eval(List<Integer> list , Predicate<Integer> predicate){
        for (Integer integer : list) {
            if(predicate.test(integer)){
                System.out.println(integer+" ");
                System.out.println(temp.apply(12,13));
                FUN fun = (a,b)-> System.out.println(a+b);
                fun.hello("asd","asd");
            }
        }
    }

}


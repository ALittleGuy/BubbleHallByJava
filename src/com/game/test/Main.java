package com.game.test;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Main {
    static Function<Integer , String> test = (a)-> new String("this is "+Integer.valueOf(a));
    static Predicate<Integer> checkGt3  = (a) -> a>3;
    static Supplier<String> getWord = ()-> new String("word");
    static Consumer<String> getNothing = (a)-> System.out.println("this is a "+a);

    public static void main(String[] args) {
        System.out.println("Function:"+test.apply(12));
        if(checkGt3.test(4) ){
            System.out.println("Predicate:"+"4 is greater than 3");
        }
        System.out.println("Supplier:"+"the funtion give me a "+getWord.get());
        getNothing.accept("word");
    }

}

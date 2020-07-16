package com.game.test;

interface B {
    public void doStringWork(String s);
}

interface C {

    public double doComputeWork(float x, float y);
}

public class A {

    public A() {
        B b = (a)-> System.out.println(a);
        b.doStringWork("哈囉");
    }

    public static void main(String[] args) {
        B b = A::printTwice;
        b.doStringWork("嗨");
        new A();

        C c = Math::pow;
        b.doStringWork(String.valueOf(c.doComputeWork(2.5f, 2)));
    }

    public static void printTwice(String s) {
        System.out.print(s);
        System.out.println(s);
    }

    public void printOnce(String s) {
        System.out.println(s);
    }
}
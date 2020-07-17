package com.game.test;

import com.game.model.ElementObj;
import com.sun.org.apache.xpath.internal.operations.Mod;

import java.lang.reflect.*;

public class Reflection {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class a = Father.class;
        try {
            a= Class.forName("com.game.model.Play");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field[] fields = a.getFields();

        for (Field field : fields) {
            int modifiers = field.getModifiers();
            System.out.println(Modifier.toString(modifiers));
            System.out.println(field.getType().getName()+ " "+field.getName());
        }
        System.out.println("------------");
        System.out.println("------------");


        Method[] methods = a.getMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            System.out.print(Modifier.toString(modifiers)+" ");
            Class returnType = method.getReturnType();
            System.out.print(returnType.getName()+ " "+ method.getName());

            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println("("+parameter.getType().getName() +" "+parameter.getName()+")");
            }

            System.out.println("------------");

        }
        System.out.println("------------");

        System.out.println(a.getDeclaredMethods().toString());
        System.out.println("------------");
        System.out.println("------------");
        System.out.println("------------");
        System.out.println("------------");

        Father father = new Father();
        father.setX(100);
        Class t = father.getClass();
        Field p = t.getField("x");
//        System.out.println(p.getInt());

        Father y = (Father) t.newInstance();
        System.out.println(y.getX());
    }
}

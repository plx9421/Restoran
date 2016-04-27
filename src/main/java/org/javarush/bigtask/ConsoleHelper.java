package com.javarush.test.level27.lesson15.big01;

import com.javarush.test.level27.lesson15.big01.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 05.04.2016.
 */
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return reader.readLine();
    }

    public static List<Dish> getAllDishesForOrder() throws IOException {
        String s = "";
        List<Dish> dishes = new ArrayList<>();
        writeMessage(Dish.allDishesToString());
        writeMessage("выберите блюдо или наберите exit для выхода: ");
        while (true){
            s = readString();
            if (s != null) {
                try {
                    dishes.add(Dish.valueOf(s));
                } catch (IllegalArgumentException ignory) {
                    if ("exit".equals(s.toLowerCase())) break;
                    writeMessage(s + " is not detected");
                    continue;
                }
            }
        }
        return dishes;
    }
}

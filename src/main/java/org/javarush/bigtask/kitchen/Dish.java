package com.javarush.test.level27.lesson15.big01.kitchen;

/**
 * Created by Alexey on 05.04.2016.
 */
public enum Dish
{
    Fish(25), Steak(30), Soup(15), Juice(5), Water(3);

    private int duration;

    Dish(int duration) { this.duration = duration; }

    public int getDuration() {
        return duration;
    }

    public static String allDishesToString(){
        Dish[] dishs = Dish.values();
        String result = "";
        for (int i = 0; i < dishs.length; i++) {
            if (i != (dishs.length - 1)) {
                result += dishs[i].toString() + ", ";
            } else {
                result += dishs[i].toString();
            }
        }
        return result;
    }
}

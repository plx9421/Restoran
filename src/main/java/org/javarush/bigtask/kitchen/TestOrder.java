package org.javarush.bigtask.kitchen;

import org.javarush.bigtask.Tablet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexey on 08.04.2016.
 */
public class TestOrder extends Order {

    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    protected void initDishes() throws IOException {
        dishes = new ArrayList<>();
        int countOfDishes = (int) (Math.random() * Dish.values().length);
        for (int i = 0; i < countOfDishes; i++)
        {
            dishes.add(Dish.values()[(int) (Math.random() * Dish.values().length)]);
        }
    }

}

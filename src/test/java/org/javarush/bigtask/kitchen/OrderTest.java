package org.javarush.bigtask.kitchen;

import com.javarush.test.level27.lesson15.big01.Tablet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 05.04.2016.
 */
public class OrderTest {

    @Test
    public void testToString() throws Exception {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Juice"));
        dishes.add(Dish.valueOf("Fish"));
        Order order = new Order(dishes, new Tablet(5));
        String expected = "Your order: [Juice, Fish] of Tablet{number=5}";
        String actual = order.toString();

        assertEquals(expected, actual);
    }


    @Test
    public void testGetTotalCookingTime() throws Exception {
//        Fish(25), Steak(30), Soup(15), Juice(5), Water(3);
        List<Dish> dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Fish"));
        dishes.add(Dish.valueOf("Steak"));
        dishes.add(Dish.valueOf("Soup"));
        dishes.add(Dish.valueOf("Juice"));
        dishes.add(Dish.valueOf("Water"));
        Order order = new Order(dishes, new Tablet(5));

        int expected = 25 + 30 + 15 + 5 + 3;
        int actual = order.getTotalCookingTime();

        assertEquals(expected, actual);

    }

    @Test
    public void testIsEmpty() throws Exception {
        Order order = new Order(null, new Tablet(5));
        boolean expected = true;
        boolean actual = order.isEmpty();
        assertEquals(expected, actual);

        order = new Order(new ArrayList<Dish>(), new Tablet(5));
        expected = true;
        actual = order.isEmpty();
        assertEquals(expected, actual);

        List<Dish> dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Fish"));
        order = new Order(dishes, new Tablet(5));
        expected = false;
        actual = order.isEmpty();
        assertEquals(expected, actual);
    }


}
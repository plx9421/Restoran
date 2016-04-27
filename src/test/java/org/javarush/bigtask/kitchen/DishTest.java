package org.javarush.bigtask.kitchen;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 05.04.2016.
 */
public class DishTest {

    @Test
    public void testAllDishesToString() throws Exception {
        String expected = "Fish, Steak, Soup, Juice, Water";
        String actual = Dish.allDishesToString();
        assertEquals(expected, actual);
    }
}
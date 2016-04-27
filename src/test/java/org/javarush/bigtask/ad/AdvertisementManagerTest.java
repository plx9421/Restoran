package org.javarush.bigtask.ad;

import org.javarush.bigtask.Tablet;
import org.javarush.bigtask.kitchen.Dish;
import org.javarush.bigtask.kitchen.Order;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 06.04.2016.
 */
public class AdvertisementManagerTest {

    private List<Advertisement> generateVideosList() {
        List<Advertisement> list = new ArrayList<>();
        Object someContent = new Object();
        list.add(new Advertisement(someContent, "1", 5000, 100, 3 * 60));
        list.add(new Advertisement(someContent, "2", 100, 10, 15 * 60));
        list.add(new Advertisement(someContent, "3", 400, 1, 10 * 60));
        list.add(new Advertisement(someContent, "4", 400, 1, 20 * 60));
        list.add(new Advertisement(someContent, "5", 400, 2, 40 * 60));
        list.add(new Advertisement(someContent, "6", 400, 2, 30 * 60));
        list.add(new Advertisement(someContent, "7", 400, 2, 50 * 60));
        list.add(new Advertisement(someContent, "8", 150, 2, 20 * 60));
        list.add(new Advertisement(someContent, "9", 7000, 2, 10 * 60));
        return list;
    }

    @Test
    public void testTestProcessVideos() throws Exception, NoVideoAvailableException {
//Test 1
        List<Dish> dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Water"));
        dishes.add(Dish.valueOf("Water"));
        Order order = new Order(dishes, new Tablet(3));
        AdvertisementManager advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        List<String> listvideos = advertisementManager.testProcessVideos();
        String actual = listvideos.toString();
        String expected = "[First Video is displaying... 50, 277]";
        assertEquals(expected, actual);

//Test 2
        dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Soup"));
        order = new Order(dishes, new Tablet(3));
        advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        listvideos = advertisementManager.testProcessVideos();
        actual = listvideos.toString();
        expected = "[Third Video is displaying... 200, 333, " +
                "First Video is displaying... 50, 277]";
        assertEquals(expected, actual);

//Test 3
        dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Juice"));
        dishes.add(Dish.valueOf("Juice"));
        order = new Order(dishes, new Tablet(3));
        advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        listvideos = advertisementManager.testProcessVideos();
        actual = listvideos.toString();
        expected = "[Third Video is displaying... 200, 333]";
        assertEquals(expected, actual);

//Test 4
        dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Juice"));
        order = new Order(dishes, new Tablet(3));
        advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        listvideos = advertisementManager.testProcessVideos();
        actual = listvideos.toString();
        expected = "[First Video is displaying... 50, 277]";
        assertEquals(expected, actual);

//Test 5
        dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Steak"));
        order = new Order(dishes, new Tablet(3));
        advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        listvideos = advertisementManager.testProcessVideos();
        actual = listvideos.toString();
        expected = "[First Video is displaying... 50, 277, " +
                "Second Video is displaying... 10, 11]";
        assertEquals(expected, actual);

//Test 6
        dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Fish"));
        dishes.add(Dish.valueOf("Fish"));
        dishes.add(Dish.valueOf("Fish"));
        order = new Order(dishes, new Tablet(3));
        advertisementManager = new AdvertisementManager(order.getTotalCookingTime() * 60);
        List<Advertisement> list = advertisementManager.getStorage().list();
        list.clear();
        list.addAll(generateVideosList());

        listvideos = advertisementManager.testProcessVideos();
        actual = listvideos.toString();
        expected = "[9 is displaying... 3500, 5833, " +
                "4 is displaying... 400, 333, " +
                "3 is displaying... 400, 666, " +
                "6 is displaying... 200, 111, " +
                "1 is displaying... 50, 277]";
        assertEquals(expected, actual);
    }
}
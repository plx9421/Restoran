package org.javarush.bigtask.statistic;

import com.javarush.test.level27.lesson15.big01.Tablet;
import com.javarush.test.level27.lesson15.big01.ad.Advertisement;
import com.javarush.test.level27.lesson15.big01.kitchen.Dish;
import com.javarush.test.level27.lesson15.big01.kitchen.Order;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventType;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 06.04.2016.
 */
public class StatisticEventManagerTest {

    @Test
    public void testGetInstance() throws Exception {
        StatisticEventManager manager = StatisticEventManager.getInstance();
        int actual = manager.getEvents().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void testRegister() throws Exception {

        List<Dish> dishes = new ArrayList<>();
        dishes.add(Dish.valueOf("Fish"));
        dishes.add(Dish.valueOf("Steak"));
        dishes.add(Dish.valueOf("Soup"));
        dishes.add(Dish.valueOf("Juice"));
        dishes.add(Dish.valueOf("Water"));
        Order order = new Order(dishes, new Tablet(5));
        StatisticEventManager manager = StatisticEventManager.getInstance();

        int expected = manager.getEvents().get(EventType.COOKED_ORDER).size();
        manager.register(new CookedOrderEventDataRow(order.getTablet().toString(), "Amigo", order.getTotalCookingTime() * 60, order.getDishes()));
        int actual = manager.getEvents().get(EventType.COOKED_ORDER).size();
        assertEquals(expected + 1, actual);

        expected = manager.getEvents().get(EventType.SELECTED_VIDEOS).size();
        manager.register(new VideoSelectedEventDataRow(new ArrayList<Advertisement>(), 1, 100));
        actual = manager.getEvents().get(EventType.SELECTED_VIDEOS).size();
        assertEquals(expected + 1, actual);
    }

    @Test
    public void testGetAmount() throws Exception {
        StatisticEventManager manager = StatisticEventManager.getInstance();

        GregorianCalendar date = new GregorianCalendar();
        date.setTime(new Date());

        date.add(Calendar.DAY_OF_MONTH, -1);
        manager.register(new VideoSelectedEventDataRow(null, 1, 100, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 2, 200, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 3, 300, date.getTime()));

        date.add(Calendar.DAY_OF_MONTH, -1);
        manager.register(new VideoSelectedEventDataRow(null, 4, 400, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 5, 500, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 6, 900, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 7, 800, date.getTime()));

        date.add(Calendar.DAY_OF_MONTH, -1);
        manager.register(new VideoSelectedEventDataRow(null, 8, 700, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 9, 600, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 10, 500, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 11, 400, date.getTime()));
        manager.register(new VideoSelectedEventDataRow(null, 12, 300, date.getTime()));

        String actual = "";
        Map<Date, Double> videoAmount = manager.getAmount();
        for (Map.Entry<Date, Double> entry : videoAmount.entrySet()) {
            actual += entry.getValue() + " ";
        }
        String expected = "0.01 0.06 0.22 0.5 ";
        assertEquals(expected, actual);

    }

    @Test
    public void testGetCookWorkloading() throws Exception {
        StatisticEventManager manager = StatisticEventManager.getInstance();

        GregorianCalendar date = new GregorianCalendar();
        date.setTime(new Date());
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Amigo", 10 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Petrov", 20 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Ivanov", 30 * 60, null, date.getTime()));

        date.add(Calendar.DAY_OF_MONTH, -1);
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Amigo", 90 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Petrov", 80 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Ivanov", 70 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Amigo", 60 * 60, null, date.getTime()));

        date.add(Calendar.DAY_OF_MONTH, -1);
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Amigo", 50 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Petrov", 40 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Ivanov", 30 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Amigo", 20 * 60, null, date.getTime()));
        manager.register(new CookedOrderEventDataRow("Tablet{number=4}", "Petrov", 10 * 60, null, date.getTime()));

        Map<Date, Map<String, Integer>> cookerTime = manager.getCookWorkloading();

        String actual = "";
        for (Map.Entry<Date, Map<String, Integer>> entry :cookerTime.entrySet()) {
            for (Map.Entry<String, Integer> cooksEntry : entry.getValue().entrySet()) {
                actual += cooksEntry.getKey() + " - " + cooksEntry.getValue() + " ";
            }
            actual += " ";
        }
        String expected = "Amigo - 10 Ivanov - 30 Petrov - 20  Amigo - 150 Ivanov - 70 Petrov - 80  Amigo - 70 Ivanov - 30 Petrov - 50  ";

        assertEquals(expected, actual);
    }
}
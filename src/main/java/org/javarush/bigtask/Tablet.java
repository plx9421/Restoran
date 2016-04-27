package org.javarush.bigtask;


import org.javarush.bigtask.ad.AdvertisementManager;
import org.javarush.bigtask.ad.NoVideoAvailableException;
import org.javarush.bigtask.kitchen.Order;
import org.javarush.bigtask.kitchen.TestOrder;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Alexey on 05.04.2016.
 */
public class Tablet{
    private final int number;
    private static Logger logger = Logger.getLogger(Tablet.class.getName());
    private LinkedBlockingQueue<Order> queue;

    public Tablet(int number) {
        this.number = number;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void createOrder() {
        try {
            Order order = new Order(this);
            orderCore(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    public void createTestOrder() {
        try {
            Order order = new TestOrder(this);
            orderCore(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable.");
        }
    }

    private void orderCore(Order order) {
        try {
            if (!order.isEmpty()) {
                ConsoleHelper.writeMessage(order.toString());
                new AdvertisementManager(order.getTotalCookingTime() * 60).processVideos();
                queue.add(order);
            }
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for the order " + order);
        }
    }

    @Override
    public String toString() {
        return "Tablet{number=" + number + "}";
    }
}
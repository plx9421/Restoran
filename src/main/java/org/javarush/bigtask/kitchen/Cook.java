package com.javarush.test.level27.lesson15.big01.kitchen;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;

import java.util.Observable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Alexey on 05.04.2016.
 */
public class Cook extends Observable implements Runnable {
    private String name;
    private LinkedBlockingQueue<Order> queue;

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setQueue(LinkedBlockingQueue<Order> queue) {
        this.queue = queue;
    }

    public void startCookingOrder(Order order) {
        int cookingTime = order.getTotalCookingTime();
        ConsoleHelper.writeMessage("Start cooking - " + order + ", cooking time " + cookingTime + "min");
        StatisticEventManager.getInstance().register(new CookedOrderEventDataRow(order.getTablet().toString(), name, cookingTime * 60, order.getDishes()));
        setChanged();
        notifyObservers(order);
        try {
            Thread.sleep(cookingTime * 10);
        } catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!queue.isEmpty()) {
                Order order = queue.poll();
                if (order != null) {
                    startCookingOrder(order);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignory) {
                if (queue.isEmpty()) break;
            }
        }
    }
}

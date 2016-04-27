package com.javarush.test.level27.lesson15.big01.statistic;

import com.javarush.test.level27.lesson15.big01.kitchen.Cook;
import com.javarush.test.level27.lesson15.big01.statistic.event.CookedOrderEventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventDataRow;
import com.javarush.test.level27.lesson15.big01.statistic.event.EventType;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.util.*;

/**
 * Created by Alexey on 06.04.2016.
 */
public class StatisticEventManager {
    private static StatisticEventManager ourInstance = new StatisticEventManager();
    private StatisticStorage storage = new StatisticStorage();

    public static StatisticEventManager getInstance() {
        return ourInstance;
    }

    //For Test. Begin
    public Map<EventType, List<EventDataRow>> getEvents() {
        return storage.events;
    }
//For Test. End

    private StatisticEventManager() {
    }

    public void register(EventDataRow data) {
        storage.put(data);
    }

    public Map<Date, Double> getAmount() {
        Map<Date, Double> amountPerDay = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> events = storage.getEvents(EventType.SELECTED_VIDEOS);
        if (events != null && !events.isEmpty()) {
            for (EventDataRow eventDataRow : events) {
                VideoSelectedEventDataRow videoSelected = (VideoSelectedEventDataRow) eventDataRow;
                Date videoDate = zeroTimeOnDate(eventDataRow.getDate());

                if (amountPerDay.containsKey(videoDate)) {
                    amountPerDay.put(videoDate, amountPerDay.get(videoDate) + (0.01d * (double) videoSelected.getAmount()));
                } else {
                    amountPerDay.put(videoDate, (0.01d * (double) videoSelected.getAmount()));
                }
            }
        }
        return amountPerDay;
    }

    public Map<Date, Map<String, Integer>> getCookWorkloading() {
        Map<Date, Map<String, Integer>> result = new TreeMap<>(Collections.reverseOrder());
        List<EventDataRow> list = storage.getEvents(EventType.COOKED_ORDER);
        if (list != null && !list.isEmpty()) {
            for (EventDataRow c : list) {
                CookedOrderEventDataRow cookedOrder = (CookedOrderEventDataRow) c;
                Date date = zeroTimeOnDate(c.getDate());
                int time = cookedOrder.getTime();

                if (time == 0) continue;
                if (time % 60 == 0) time = time / 60;
                else time = time / 60 + 1;

                if (result.containsKey(date)) {
                    Map<String, Integer> cookInfo = result.get(date);
                    if (cookInfo.containsKey(cookedOrder.getCookName()))
                        cookInfo.put(cookedOrder.getCookName(), cookInfo.get(cookedOrder.getCookName()) + time);
                    else cookInfo.put(cookedOrder.getCookName(), time);
                } else {
                    TreeMap<String, Integer> cookInfo = new TreeMap<>();
                    cookInfo.put(cookedOrder.getCookName(), time);
                    result.put(date, cookInfo);
                }
            }
        }
        return result;
    }

    private Date zeroTimeOnDate(Date date) {
        GregorianCalendar result = new GregorianCalendar();
        result.setTime(date);
        result.set(Calendar.HOUR_OF_DAY, 0);
        result.set(Calendar.MINUTE, 0);
        result.set(Calendar.SECOND, 0);
        result.set(Calendar.MILLISECOND, 0);
        return result.getTime();
    }

    private class StatisticStorage {
        Map<EventType, List<EventDataRow>> events = new TreeMap<>();

        public StatisticStorage() {
            for (EventType event : EventType.values()) {
                events.put(event, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            if (data == null) return;
            events.get(data.getType()).add(data);
        }

        public List<EventDataRow> getEvents(EventType eventType) {
            return events.get(eventType);
        }
    }
}

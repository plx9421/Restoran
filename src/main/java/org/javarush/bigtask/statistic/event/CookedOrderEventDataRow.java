package org.javarush.bigtask.statistic.event;

import org.javarush.bigtask.kitchen.Dish;

import java.util.Date;
import java.util.List;

/**
 * Created by Alexey on 06.04.2016.
 */
public class CookedOrderEventDataRow implements EventDataRow{
    private String tabletName; //имя планшета, используйте tablet.toString()
    private String cookName; //имя повара
    private int cookingTimeSeconds;//время приготовления заказа в секундах
    private List<Dish> cookingDishs;//список блюд для приготовления
    private Date currentDate;

    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate = new Date();
    }
//Constructor for Tests
    public CookedOrderEventDataRow(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishs, Date currentDate) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishs = cookingDishs;
        this.currentDate = currentDate;
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return currentDate;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}

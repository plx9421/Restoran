package com.javarush.test.level27.lesson15.big01.ad;

/**
 * Created by Alexey on 05.04.2016.
 */
public class Advertisement {
    private Object content;// - видео
    private String name;// - имя/название
    private long initialAmount;// - начальная сумма, стоимость рекламы в копейках. Используем long, чтобы избежать проблем с округлением
    private int hits;// - количество оплаченных показов
    private int duration;// - продолжительность в секундах
    private long amountPerOneDisplaying; //стоимости одного показа рекламного объявления в копейках

    public Advertisement(Object content, String name, long initialAmount, int hits, int duration) {
        this.content = content;
        this.name = name;
        this.initialAmount = initialAmount;
        this.hits = hits;
        this.duration = duration;

        this.amountPerOneDisplaying = initialAmount;
        if (hits != 0)
            this.amountPerOneDisplaying = initialAmount / hits; //стоимость одного показа рекламного объявления в копейках.
        else this.amountPerOneDisplaying = 0;

    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public long getAmountPerOneDisplaying() {
        return amountPerOneDisplaying;
    }

    public void revalidate() {
        if (hits <= 0) throw new UnsupportedOperationException();
        hits--;
        if (hits == 1) {
            amountPerOneDisplaying += initialAmount % amountPerOneDisplaying; }
    }

    public int getHits() {
        return hits;
    }
}

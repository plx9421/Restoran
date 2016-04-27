package com.javarush.test.level27.lesson15.big01.ad;

import com.javarush.test.level27.lesson15.big01.ConsoleHelper;
import com.javarush.test.level27.lesson15.big01.statistic.StatisticEventManager;
import com.javarush.test.level27.lesson15.big01.statistic.event.VideoSelectedEventDataRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexey on 05.04.2016.
 */
// - у каждого планшета будет свой объект менеджера,
//который будет подбирать оптимальный набор роликов и их последовательность для каждого заказа.
//        Он также будет взаимодействовать с плеером и отображать ролики

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    private long maxProfit = 0;
    private int minRemainingTime = timeSeconds;
    private List<Advertisement> videos = storage.list();

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

//    Для модуля тестов - Начало
    private List<String> outPutList = new ArrayList<>();

    AdvertisementStorage getStorage() {
        return storage;
    }

    List<String> testProcessVideos() throws NoVideoAvailableException {
        processVideos();
        return outPutList;
    }
//Для модуля тестов - Конец

    public void processVideos() throws NoVideoAvailableException {
        if (storage.list() == null || storage.list().isEmpty()) throw new NoVideoAvailableException();

        List<Advertisement> selected = new ArrayList<>();
        selected = getOptimalAdvList(null, null, timeSeconds, 0, selected);

        long amount = 0;
        int totalDuration = 0;

        for (Advertisement advertisement : selected) {
            amount += advertisement.getAmountPerOneDisplaying();
            totalDuration += advertisement.getDuration();
            String result = String.format("%s is displaying... %d, %d",
                    advertisement.getName(),
                    advertisement.getAmountPerOneDisplaying(),
                    1000 * advertisement.getAmountPerOneDisplaying() / advertisement.getDuration());

            outPutList.add(result);
            ConsoleHelper.writeMessage(result);
            advertisement.revalidate();
        }
        StatisticEventManager.getInstance().register(new VideoSelectedEventDataRow(selected, amount, totalDuration));

    }

    private List<Advertisement> getOptimalAdvList(List<Advertisement> previousList, Advertisement previousAd, int remainingTime,
                                                  long profit, List<Advertisement> bestResult) throws NoVideoAvailableException {
        List<Advertisement> newList = new ArrayList<>();
        if (previousList != null) {
            newList.addAll(previousList);
            remainingTime -= previousAd.getDuration();
            profit += previousAd.getAmountPerOneDisplaying();
            newList.add(previousAd);
        }
        for (Advertisement ad : videos) {
            if (remainingTime == 0) break;
            if (newList.contains(ad)) continue;
            if (ad.getHits() <= 0) continue;
            if (remainingTime >= ad.getDuration())
                bestResult = getOptimalAdvList(newList, ad, remainingTime, profit, bestResult);
        }
        if (profit > maxProfit) {
            maxProfit = profit;
            minRemainingTime = remainingTime;
            bestResult = newList;
        } else if (profit == maxProfit && remainingTime < minRemainingTime) {
            minRemainingTime = remainingTime;
            bestResult = newList;
        } else if (profit == maxProfit && remainingTime == minRemainingTime && bestResult.size() > newList.size())
            bestResult = newList;
        if (bestResult.isEmpty()) {
//            StatisticEventManager.getInstance().register(new NoAvailableVideoEventDataRow(timeSeconds));
            throw new NoVideoAvailableException();
        }
        Collections.sort(bestResult, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                long pricePerVideoDiff = o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying();
                if (pricePerVideoDiff != 0)
                    return (int) pricePerVideoDiff;
                else
                    return (int) (o1.getAmountPerOneDisplaying() * 100 / o1.getDuration() - o2.getAmountPerOneDisplaying() * 100 / o2.getDuration());
            }
        });
        return bestResult;
    }

}

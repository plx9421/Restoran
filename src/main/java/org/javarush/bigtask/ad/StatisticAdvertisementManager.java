package com.javarush.test.level27.lesson15.big01.ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 08.04.2016.
 */
public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    //For Tests only
    public List<Advertisement> getAdvertisementStorageList() {
        return advertisementStorage.list();
    }

    public List<Advertisement> getAdvertisement(boolean isActive) {
        List<Advertisement> list = advertisementStorage.list();
        List<Advertisement> result = new ArrayList<>();

        for (Advertisement advertisement : list) {
            if (isActive && advertisement.getHits() > 0) result.add(advertisement);
            if (!isActive && advertisement.getHits() <= 0) result.add(advertisement);
        }
        return result;
    }

    private StatisticAdvertisementManager() {
    }
}

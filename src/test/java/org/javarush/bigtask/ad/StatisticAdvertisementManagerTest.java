package org.javarush.bigtask.ad;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 08.04.2016.
 */
public class StatisticAdvertisementManagerTest {
    private List<Advertisement> generateVideosList() {
        List<Advertisement> list = new ArrayList<>();
        Object someContent = new Object();
        list.add(new Advertisement(someContent, "First Video", 5000, 100, 3 * 60));
        list.add(new Advertisement(someContent, "second video", 100, 10, 15 * 60));
        list.add(new Advertisement(someContent, "Third Video", 400, 2, 10 * 60));
        list.add(new Advertisement(someContent, "четвертое видео", 400, 4, 20 * 60));
        list.add(new Advertisement(someContent, "5", 400, 0, 40 * 60));
        list.add(new Advertisement(someContent, "6", 400, 1, 30 * 60));
        list.add(new Advertisement(someContent, "7", 400, 0, 50 * 60));
        list.add(new Advertisement(someContent, "8", 150, 0, 20 * 60));
        list.add(new Advertisement(someContent, "9", 7000, 0, 10 * 60));
        return list;
    }

    @Test
    public void testGetAdvertisement() throws Exception {

        StatisticAdvertisementManager statisticAdvertisementManager = StatisticAdvertisementManager.getInstance();

        List<Advertisement> list = statisticAdvertisementManager.getAdvertisementStorageList();
        list.clear();
        list.addAll(generateVideosList());

        List<Advertisement> actualList = statisticAdvertisementManager.getAdvertisement(true);
        assertEquals(list.size() - 4, actualList.size());

        actualList = statisticAdvertisementManager.getAdvertisement(false);
        assertEquals(4, actualList.size());

//        String actual = actualList.toString();
//        String expected = "";
//        assertEquals(expected, actual);

    }

}
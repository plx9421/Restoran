package org.javarush.bigtask.ad;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 05.04.2016.
 */
public class AdvertisementStorageTest {

    @Test
    public void testGetInstance() throws Exception {
        AdvertisementStorage storage = AdvertisementStorage.getInstance();
        assertNotNull(storage);
    }

    @Test
    public void testList() throws Exception {
        AdvertisementStorage storage = AdvertisementStorage.getInstance();
        List<Advertisement> actual = storage.list();
        assertFalse(storage.list().isEmpty());
    }

    @Test
    public void testAdd() throws Exception {
        AdvertisementStorage storage = AdvertisementStorage.getInstance();
        List<Advertisement> actual = storage.list();
        int size = actual.size();

        Object someContent = new Object();
        actual.add(new Advertisement(someContent, "4 Video", 5000, 400, 4 * 60)); // 3 min

        assertEquals(size + 1, actual.size());
    }
}
package org.javarush.bigtask.ad;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 08.04.2016.
 */
public class AdvertisementTest {

    @Test
    public void testRevalidate() throws Exception {
        Object someContent = new Object();
        long initialAmount = 125;
        int hits = 3;
//hits = 3
        Advertisement advertisement = new Advertisement(someContent, "Test Video", initialAmount, hits, 3 * 60);
        int expectedHits = advertisement.getHits();
        advertisement.revalidate();
        int actualHits = advertisement.getHits();
        assertEquals(expectedHits - 1, actualHits);
//hits = 2
        long expectedAmountPerOneDisplaying = (initialAmount % hits) + advertisement.getAmountPerOneDisplaying();
        advertisement.revalidate();
        long actualAmountPerOneDisplaying = advertisement.getAmountPerOneDisplaying();
        assertEquals(expectedAmountPerOneDisplaying, actualAmountPerOneDisplaying);
//hits = 1
        actualAmountPerOneDisplaying = advertisement.getAmountPerOneDisplaying();
        advertisement.revalidate();
        expectedAmountPerOneDisplaying = advertisement.getAmountPerOneDisplaying();
        assertEquals(expectedAmountPerOneDisplaying, actualAmountPerOneDisplaying);
//hits = 0
        boolean flag = false;
        try {
            advertisement.revalidate();
        } catch (UnsupportedOperationException ignory) {
            flag = true;
        }
        assertTrue(flag);

    }
}
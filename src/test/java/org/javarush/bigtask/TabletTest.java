package org.javarush.bigtask;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 05.04.2016.
 */
public class TabletTest {

    @Test
    public void testGetNumber() throws Exception {

    }

    @Test
    public void testToString() throws Exception {
        String expected = "Tablet{number=4}";
        String actual = new Tablet(4).toString();

        assertEquals(expected, actual);

    }
}
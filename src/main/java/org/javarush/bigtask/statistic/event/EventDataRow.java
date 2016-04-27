package org.javarush.bigtask.statistic.event;

import java.util.Date;

/**
 * Created by Alexey on 06.04.2016.
 */
public interface EventDataRow {
    EventType getType();

    Date getDate();

    int getTime();
}

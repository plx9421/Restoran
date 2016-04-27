package org.javarush.bigtask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 08.04.2016.
 */
public class RandomOrderGeneratorTask implements Runnable {
    private List<Tablet> tablets = new ArrayList<>();
    private int interval;

    public RandomOrderGeneratorTask(List<Tablet> tablets, int interval) {
        this.tablets = tablets;
        this.interval = interval;
    }

    @Override
    public void run() {
        while (true) {
            tablets.get((int) (Math.random() * tablets.size())).createTestOrder();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException ignory) {
                break;
            }
        }
    }
}

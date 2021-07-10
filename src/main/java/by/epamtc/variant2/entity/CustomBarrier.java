package by.epamtc.variant2.entity;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class CustomBarrier {

    private int threadLimit;

    private int threadsReached;

    public CustomBarrier(int threadLimit) {
        this.threadLimit = threadLimit;
    }

    public synchronized void await() throws InterruptedException {
        threadsReached++;
        if (threadLimit == threadsReached) {
            notifyAll();
        } else {
            System.out.println("-------------reached " + Thread.currentThread().getName());

            wait();
        }
    }

    public boolean isBroken() {
        return threadLimit == threadsReached;
    }

}

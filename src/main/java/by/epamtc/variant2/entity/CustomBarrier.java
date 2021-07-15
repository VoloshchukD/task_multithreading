package by.epamtc.variant2.entity;

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
            wait();
        }
    }

    public boolean isBroken() {
        return threadLimit == threadsReached;
    }

    public void reset() {
        threadsReached = 0;
    }

}

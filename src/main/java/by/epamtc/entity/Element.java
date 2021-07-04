package by.epamtc.entity;

import java.util.concurrent.locks.ReentrantLock;

public class Element {

    private int value;

    private static ReentrantLock lock = new ReentrantLock();

    public int getValue() {
        return value;
    }

    public void changeValue(int value) {
        if (lock.tryLock()) {
            this.value = value;
        }
    }

    public void unlock() {
        if (lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

}

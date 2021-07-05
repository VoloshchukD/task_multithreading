package by.epamtc.service;

import java.util.concurrent.locks.Lock;

public class MatrixWriterExecutor {

    public void run(Lock lock) {
        lock.lock();
        lock.newCondition().signal();
        lock.unlock();
    }

}

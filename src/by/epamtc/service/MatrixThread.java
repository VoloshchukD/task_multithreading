package by.epamtc.service;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MatrixThread implements Callable<Integer> {

    private int threadId;

    private MatrixAction action;

    public MatrixThread( int threadId,MatrixAction action) {
        this.threadId = threadId;
        this.action = action;
    }

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().setName((Integer.toString(threadId)));
        int result = action.proceed();
        TimeUnit.MILLISECONDS.sleep(100);
        return result;
    }

}

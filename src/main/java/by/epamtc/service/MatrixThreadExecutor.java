package by.epamtc.service;

import by.epamtc.service.thread.MatrixThread;
import by.epamtc.service.thread.WriterThread;

import java.util.ArrayList;
import java.util.concurrent.*;

public class MatrixThreadExecutor {

    private int threadsPerPhase;

    private MatrixThread[] threads;

    private ExecutorService executorService;

    public MatrixThreadExecutor(int threadsPerPhase, MatrixThread[] threads) {
        this.threadsPerPhase = threadsPerPhase;
        this.threads = threads;
        this.executorService = Executors.newFixedThreadPool(threadsPerPhase);
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < (threads.length / threadsPerPhase); i++) {
            ArrayList<Future<Integer>> list = new ArrayList<>();
            WriterThread writerThread = new WriterThread(list);
            CyclicBarrier barrier = new CyclicBarrier(threadsPerPhase, writerThread);
            for (int j = 0; j < threadsPerPhase; j++) {
                threads[j].setBarrier(barrier);
                Future<Integer> future = executorService.submit(threads[j]);
                list.add(future);
            }
            writerThread.join();
        }
    }

}

package by.epamtc.service;

import by.epamtc.service.thread.MatrixThread;
import by.epamtc.util.Randomizer;

import java.util.ArrayList;
import java.util.concurrent.*;

public class MatrixThreadExecutor {

    private int threadsAmount;

    private ExecutorService executorService;

    private CyclicBarrier barrier;

    public MatrixThreadExecutor(int threadsAmount) {
        this.threadsAmount = threadsAmount;
        this.executorService = Executors.newFixedThreadPool(threadsAmount);
        this.barrier = new CyclicBarrier(threadsAmount);
    }

    public ArrayList<Future<Integer>> run() throws ExecutionException, InterruptedException {
        ArrayList<Future<Integer>> list = new ArrayList<>();
        for (int i = 1; i <= threadsAmount; i++) {
            MatrixThread thread = new MatrixThread(i, Randomizer.generateNumber(threadsAmount),
                    Randomizer.generateNumber(threadsAmount), Randomizer.generateBoolean());
            Future<Integer> future = executorService.submit(thread);
            list.add(future);
        }
        executorService.shutdown();

        for (Future<Integer> future : list) {
            System.out.println(future.get() + " result fixed");
        }

        return list;
    }

}

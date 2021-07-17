package by.epamtc.variant2.service;

import by.epamtc.variant2.entity.thread.MatrixThread;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CustomExecutorService {

    public Map<Integer, Integer> invokeAll(List<MatrixThread> threads) throws InterruptedException {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
        return awaitTerminated(threads);
    }

    private Map<Integer, Integer> awaitTerminated(List<MatrixThread> threads) throws InterruptedException {
        Map<Integer, Integer> result = new LinkedHashMap<>();
        for (int i = 0; i < threads.size(); i++) {
            MatrixThread thread = threads.get(i);
            if (thread.getState() != Thread.State.TERMINATED) {
                thread.join();
            }
            result.put(Integer.parseInt(thread.getName()), thread.getSumResult());
        }
        return result;
    }

}

package by.epamtc.variant2.entity.thread;

import java.util.List;

public class CustomExecutorService {

    public void invokeAll(List<MatrixThread> threads) throws InterruptedException {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }
        awaitTerminated(threads);
    }

    private void awaitTerminated(List<MatrixThread> threads) throws InterruptedException {
        for (int i = 0; i < threads.size(); i++) {
            MatrixThread thread = threads.get(i);
            if (thread.getState() != Thread.State.TERMINATED) {
                thread.join();
            }
        }
    }

}

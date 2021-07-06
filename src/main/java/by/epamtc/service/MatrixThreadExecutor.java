package by.epamtc.service;

import by.epamtc.dao.impl.MatrixChangeDaoImpl;
import by.epamtc.dao.impl.MatrixDaoImpl;
import by.epamtc.entity.Matrix;
import by.epamtc.service.thread.MatrixThread;
import by.epamtc.service.thread.WriterThread;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MatrixThreadExecutor {

    private int threadsPerPhase;

    private Matrix matrix;

    private MatrixThread[] threads;

    private ExecutorService executorService;

    public MatrixThreadExecutor(Matrix matrix, int threadsPerPhase, MatrixThread[] threads) {
        this.matrix = matrix;
        this.threadsPerPhase = threadsPerPhase;
        this.threads = threads;
        this.executorService = Executors.newFixedThreadPool(threadsPerPhase);
    }

    public void run() throws InterruptedException {
        for (int i = 0; i < (threads.length / threadsPerPhase); i++) {
            Map<Integer, Future<Integer>> map = new LinkedHashMap<>();

//            WriterThread writerThread = new WriterThread(matrix, map2);
            CyclicBarrier barrier = new CyclicBarrier(threadsPerPhase);
            for (int j = 0; j < threadsPerPhase; j++) {
                threads[j].setMatrix(matrix);
                threads[j].setBarrier(barrier);
                Future<Integer> future = executorService.submit(threads[j]);
                map.put(threads[j].getThreadId(), future);
            }


            Map<Integer, Integer> map2 = new LinkedHashMap<>();
            try {
                for (Map.Entry<Integer, Future<Integer>> entry : map.entrySet()) {
                    map2.put(entry.getKey(), entry.getValue().get());
                }
                System.out.println(map2);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            write(matrix, map2);
//            writerThread.join();
        }

    }

    public void write(Matrix matrix, Map<Integer, Integer> data) {
        MatrixDaoImpl matrixDao = new MatrixDaoImpl();
        try {
            matrixDao.writeMatrix(matrix);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("...write... - " + data.toString());

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            stringBuilder.append("thread ").append(entry.getKey()).append(" ");
            stringBuilder.append("result sum ").append(entry.getValue());
        }
        System.out.println();
        stringBuilder.append("\n");
        stringBuilder.append(matrix);
        MatrixChangeDaoImpl matrixChangeDao = new MatrixChangeDaoImpl();
        try {
            matrixChangeDao.writeMatrixChange(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

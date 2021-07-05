package by.epamtc.run;

import by.epamtc.entity.Element;
import by.epamtc.entity.Matrix;
import by.epamtc.service.MatrixThreadExecutor;
import by.epamtc.service.thread.MatrixThread;
import by.epamtc.util.Randomizer;

import java.util.concurrent.*;

public class Runner {
    public static void main(String[] args) throws ExecutionException, InterruptedException, BrokenBarrierException {
        int matrixSize = 4;
        Matrix matrix = Matrix.getInstance();

        Element[][] elements = new Element[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                elements[i][j] = new Element();
            }
        }

        matrix.setValues(elements);

//        MatrixAction matrixAction = new MatrixAction(matrix, 1,
//                2, false);
//        MatrixThread matrixThread = new MatrixThread(matrixAction);
//        System.out.println(matrix);
//        ExecutorService executorService = Executors.newFixedThreadPool(matrixSize);
//        MatrixThread thread = new MatrixThread(i, Randomizer.generateNumber(threadsPerPhase),
//                Randomizer.generateNumber(threadsPerPhase), Randomizer.generateBoolean());


        MatrixThread[] threads = new MatrixThread[matrixSize * 3];
        for (int i = 0; i < threads.length; i++) {
            MatrixThread thread = new MatrixThread(i, Randomizer.generateNumber(matrixSize),
                Randomizer.generateNumber(matrixSize), Randomizer.generateBoolean());
            threads[i] = thread;
        }

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrixSize, threads);
        matrixThreadExecutor.run();

//        for (Future<Integer> future : list) {
//            System.out.println(future.get() + " result fixed");
//        }

//        CyclicBarrier barrier = new CyclicBarrier(matrixSize);
//        barrier.await();

    }
}

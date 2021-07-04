package by.epamtc.run;

import by.epamtc.entity.Element;
import by.epamtc.entity.Matrix;
import by.epamtc.service.MatrixThreadExecutor;

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

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrixSize);
        matrixThreadExecutor.run();

//        for (Future<Integer> future : list) {
//            System.out.println(future.get() + " result fixed");
//        }

//        CyclicBarrier barrier = new CyclicBarrier(matrixSize);
//        barrier.await();

    }
}

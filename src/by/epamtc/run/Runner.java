package by.epamtc.run;

import by.epamtc.entity.Matrix;
import by.epamtc.service.MatrixAction;
import by.epamtc.service.MatrixThread;
import by.epamtc.util.Randomizer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int matrixSize = 4;
        Matrix matrix = new Matrix(matrixSize);
//
//        MatrixAction matrixAction = new MatrixAction(matrix, 1,
//                2, false);
//        MatrixThread matrixThread = new MatrixThread(matrixAction);
//        System.out.println(matrix);


        ExecutorService executorService = Executors.newFixedThreadPool(matrixSize);
//        System.out.println("ms " + Randomizer.generateNumber(matrixSize));
//        System.out.println("ms " + Randomizer.generateNumber(matrixSize));
//        System.out.println("ms " + Randomizer.generateNumber(matrixSize));
//        System.out.println("ms " + Randomizer.generateNumber(matrixSize));

        ArrayList<Future<Integer>> list = new ArrayList<Future<Integer>>();

        for (int i = 1; i <= matrixSize; i++) {
            MatrixAction matrixAction = new MatrixAction(matrix, Randomizer.generateNumber(matrixSize),
                    Randomizer.generateNumber(matrixSize), true);
            MatrixThread thread = new MatrixThread(i, matrixAction);
            Future<Integer> future = executorService.submit(thread);
            list.add(future);
        }
        executorService.shutdown();
        for (Future<Integer> future : list) {
            System.out.println(future.get() + " result fixed");
        }

        System.out.println(matrix.toString());

    }
}

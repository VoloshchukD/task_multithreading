package by.epamtc.run;

import by.epamtc.entity.EditData;
import by.epamtc.entity.Element;
import by.epamtc.entity.Matrix;
import by.epamtc.service.MatrixThreadExecutor;
import by.epamtc.service.thread.MatrixThread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Runner {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        int matrixSize = 4;
//        Matrix matrix = Matrix.getInstance();
//
//        Element[][] elements = new Element[matrixSize][matrixSize];
//        for (int i = 0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                elements[i][j] = new Element();
//            }
//        }
//        matrix.setValues(elements);
//
//        ArrayList<Future<Integer>> list = new ArrayList<>();
//        ExecutorService es = Executors.newFixedThreadPool(matrixSize);
//        for (int i = 1; i <= 4; i++) {
//            MatrixThread mt = new MatrixThread(i, random(matrixSize-1), random(matrixSize-1), true);
//            mt.setMatrix(matrix);
//            list.add(es.submit(mt));
//
//        }
//        es.shutdown();
//        for (Future<Integer> future : list) {
//            System.out.println(future.get() + " result fixed");
//        }



        int matrixSize = 4;
        Matrix matrix = Matrix.getInstance();

        Element[][] elements = new Element[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                elements[i][j] = new Element();
            }
        }
        matrix.setValues(elements);

        EditData[] editData = new EditData[matrixSize * 3];
        for (int i = 0; i < editData.length; i++) {
            EditData data = new EditData();
            data.setMutableIndex(random(matrix.size()));
            data.setDiagonalIndex(random(matrix.size()));
            data.setRowMutable(random(1) == 1);
            editData[i] = data;
        }

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrix, editData, matrix.size());
        matrixThreadExecutor.run();

    }

    public static int random(int upperBorder) {
        return 0 + (int) (Math.random() * upperBorder);
    }

}

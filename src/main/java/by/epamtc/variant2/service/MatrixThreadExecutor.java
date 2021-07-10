package by.epamtc.variant2.service;

import by.epamtc.variant1.dao.impl.MatrixDaoImpl;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant2.entity.CustomBarrier;
import by.epamtc.variant2.entity.thread.MatrixThread;
import by.epamtc.variant1.service.PhaseWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MatrixThreadExecutor {




//    private int editsPerPhase;
//
//    private Matrix matrix;
//
//    private EditData[] editData;
//
//    private ExecutorService executorService;
//
//    private PhaseWriter phaseWriter;
//
//    public MatrixThreadExecutor(Matrix matrix, EditData[] editData, int editsPerPhase) {
//        this.matrix = matrix;
//        this.editData = editData;
//        this.editsPerPhase = editsPerPhase;
//        this.executorService = Executors.newFixedThreadPool(editsPerPhase);
//        phaseWriter = PhaseWriter.getInstance();
//    }

    public void run() throws InterruptedException {
        for (int i = 1; i <= 2; i++) {
            System.out.println("//////PHASE//////// " + i);
            CustomBarrier customBarrier = new CustomBarrier(5);
            for (int j = 1; j <= 5; j++) {
                MatrixThread matrixThread = new MatrixThread();
                matrixThread.setBarrier(customBarrier);
                matrixThread.start();
            }
            while (!customBarrier.isBroken()) {
                TimeUnit.SECONDS.sleep(3);
            }

        }

    }
//
//    public void run() {
//        int currentThreadIndex = 0;
//        for (int i = 0; i < (editData.length / editsPerPhase); i++) {
//            Map<Integer, Future<Integer>> map = new LinkedHashMap<>();
//            CyclicBarrier barrier = new CyclicBarrier(editsPerPhase);
//            for (int j = 0; j < editsPerPhase; j++, currentThreadIndex++) {
//                MatrixThread matrixThread = new MatrixThread(matrix, editData[currentThreadIndex], barrier);
//                Future<Integer> future = executorService.submit(matrixThread);
//                map.put(matrixThread.getThreadId(), future);
//            }
//            saveMatrix(matrix);
//            phaseWriter.writeResult(matrix, map);
//        }
//
//    }
//
//    public void saveMatrix(Matrix matrix) {
//        MatrixDaoImpl matrixDao = new MatrixDaoImpl();
//        try {
//            matrixDao.writeMatrix(matrix);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}

package by.epamtc.variant2.service;

import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.entity.CustomBarrier;
import by.epamtc.variant2.entity.ProxyMatrix;
import by.epamtc.variant2.entity.thread.MatrixThread;

import java.util.concurrent.*;

public class MatrixThreadExecutor {

    private int editsPerPhase;

    private ProxyMatrix proxyMatrix;

    private EditData[] editData;

    public MatrixThreadExecutor(int editsPerPhase, Matrix matrix, EditData[] editData) {
        this.editsPerPhase = editsPerPhase;
        this.proxyMatrix = new ProxyMatrix(matrix);
        this.editData = editData;
    }

    public void run() throws InterruptedException {
        int currentEditDataIndex = 0;
        for (int i = 0; i < editData.length / editsPerPhase; i++) {
            System.out.println("//////PHASE//////// " + i);
            CustomBarrier customBarrier = new CustomBarrier(editsPerPhase);
            for (int j = 0; j < editsPerPhase; j++) {
                MatrixThread matrixThread = new MatrixThread(proxyMatrix, editData[currentEditDataIndex]);
                currentEditDataIndex++;
                matrixThread.setBarrier(customBarrier);
                matrixThread.start();
            }
            while (!customBarrier.isBroken()) {
                TimeUnit.SECONDS.sleep(3);
            }
            System.out.println(proxyMatrix.toString());
            System.out.println(proxyMatrix.getSumResult());
            proxyMatrix.resetSumResult();
        }
    }


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



}

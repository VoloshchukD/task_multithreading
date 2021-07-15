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
        CustomBarrier customBarrier = new CustomBarrier(editsPerPhase);
        for (int i = 0; i < editData.length / editsPerPhase; i++) {
            System.out.println("//////PHASE//////// " + i);

            for (int j = 0; j < editsPerPhase; j++) {
                MatrixThread matrixThread = new MatrixThread(proxyMatrix, editData[currentEditDataIndex]);
                currentEditDataIndex++;
                matrixThread.setBarrier(customBarrier);
                matrixThread.start();
            }
            while (!customBarrier.isBroken()) {
                TimeUnit.SECONDS.sleep(1);
            }
            customBarrier.reset();
            System.out.println(proxyMatrix.toString());
            System.out.println(proxyMatrix.getSumResult());

            PhaseWriter phaseWriter = PhaseWriter.getInstance();
            phaseWriter.writeResult(proxyMatrix.getMatrix(), proxyMatrix.getSumResult());
            proxyMatrix.resetSumResult();
        }
    }

}

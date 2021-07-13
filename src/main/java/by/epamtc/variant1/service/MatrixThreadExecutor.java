package by.epamtc.variant1.service;

import by.epamtc.variant1.dao.impl.MatrixDaoImpl;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.entity.thread.MatrixThread;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.*;

public class MatrixThreadExecutor {

    private int editsPerPhase;

    private Matrix matrix;

    private EditData[] editData;

    private ExecutorService executorService;

    private PhaseWriter phaseWriter;

    public MatrixThreadExecutor(Matrix matrix,EditData[] editData, int editsPerPhase) {
        this.matrix = matrix;
        this.editData = editData;
        this.editsPerPhase = editsPerPhase;
        this.executorService = Executors.newFixedThreadPool(editsPerPhase);
        phaseWriter = new PhaseWriter();
    }

    public void run() throws InterruptedException {
        int currentThreadIndex = 0;
        CyclicBarrier barrier = new CyclicBarrier(editsPerPhase);
        for (int i = 0; i < (editData.length / editsPerPhase); i++) {
            Map<Integer, Future<Integer>> map = new LinkedHashMap<>();

            for (int j = 0; j < editsPerPhase; j++, currentThreadIndex++) {
                MatrixThread matrixThread = new MatrixThread(matrix, editData[currentThreadIndex], barrier);
                Future<Integer> future = executorService.submit(matrixThread);
                map.put(matrixThread.getThreadId(), future);
            }
            barrier.reset();
            phaseWriter.writeResult(matrix, map);
        }
        executorService.shutdown();
    }

}

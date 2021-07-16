package by.epamtc.variant2.service;

import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.entity.ProxyMatrix;
import by.epamtc.variant2.entity.thread.MatrixThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MatrixThreadExecutor {

    private int editsPerPhase;

    private ProxyMatrix proxyMatrix;

    private Queue<EditData> editData;

    public MatrixThreadExecutor(int editsPerPhase, Matrix matrix, Queue<EditData> editData) {
        this.editsPerPhase = editsPerPhase;
        this.proxyMatrix = new ProxyMatrix(matrix);
        this.editData = editData;
    }

    public void run() throws InterruptedException {
        int iterations = editData.size();
        CustomExecutorService executorService = new CustomExecutorService();
        for (int i = 0; i < (iterations / editsPerPhase); i++) {
            List<MatrixThread> phaseThreads = initializePhaseThreads();
            Map<Integer, Integer> sumResults = executorService.invokeAll(phaseThreads);
            writeResult(sumResults);
        }
    }

    private List<MatrixThread> initializePhaseThreads() {
        ArrayList<MatrixThread> phaseThreads = new ArrayList<>();
        for (int i = 0; i < editsPerPhase; i++) {
            MatrixThread matrixThread = new MatrixThread(proxyMatrix, editData.poll());
            phaseThreads.add(matrixThread);
        }
        phaseThreads.trimToSize();
        return phaseThreads;
    }

    private void writeResult(Map<Integer, Integer> sumResults) {
        PhaseWriter phaseWriter = new PhaseWriter();
        phaseWriter.writeResult(proxyMatrix.getMatrix(), sumResults);
    }

}

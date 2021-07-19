package by.epamtc.variant2.service;

import by.epamtc.variant1.exception.ServiceException;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.entity.ProxyMatrix;
import by.epamtc.variant2.entity.thread.MatrixThread;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class MatrixService {

    private int editsPerPhase;

    private ProxyMatrix proxyMatrix;

    private Queue<EditData> editData;

    private static final Logger logger = LogManager.getLogger();

    public MatrixService(int editsPerPhase, Matrix matrix, Queue<EditData> editData) {
        this.editsPerPhase = editsPerPhase;
        this.proxyMatrix = new ProxyMatrix(matrix);
        this.editData = editData;
    }

    public void run() throws InterruptedException, ServiceException {
        int iterations = editData.size();
        CustomExecutorService executorService = new CustomExecutorService();
        for (int i = 0; i < (iterations / editsPerPhase); i++) {
            List<MatrixThread> phaseThreads = initializePhaseThreads();
            Map<Integer, Integer> sumResults = executorService.invokeAll(phaseThreads);
            writeResult(sumResults);
        }
        logger.log(Level.INFO, "Matrix threads executed");
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

    private void writeResult(Map<Integer, Integer> sumResults) throws ServiceException {
        PhaseWriter phaseWriter = new PhaseWriter();
        phaseWriter.writeResult(proxyMatrix.getMatrix(), sumResults);
    }

}

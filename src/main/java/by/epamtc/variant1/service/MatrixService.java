package by.epamtc.variant1.service;

import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.entity.thread.MatrixThread;
import by.epamtc.variant1.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;

public class MatrixService {

    private int editsPerPhase;

    private Matrix matrix;

    private Queue<EditData> editData;

    private static final Logger logger = LogManager.getLogger();

    public MatrixService(Matrix matrix, Queue<EditData> editData, int editsPerPhase) {
        this.editsPerPhase = editsPerPhase;
        this.matrix = matrix;
        this.editData = editData;
    }

    public void execute() throws InterruptedException, ExecutionException, ServiceException {
        int iterations = editData.size();
        ExecutorService executorService = Executors.newFixedThreadPool(editsPerPhase);
        for (int i = 0; i < (iterations / editsPerPhase); i++) {
            List<MatrixThread> phaseThreads = initializePhaseThreads();
            executorService.invokeAll(phaseThreads);
            writeMatrix();
        }
        logger.log(Level.INFO, "Matrix threads executed");
        executorService.shutdown();
    }

    private List<MatrixThread> initializePhaseThreads() {
        ArrayList<MatrixThread> phaseThreads = new ArrayList<>();
        for (int i = 0; i < editsPerPhase; i++) {
            MatrixThread matrixThread = new MatrixThread(matrix, editData.poll());
            phaseThreads.add(matrixThread);
        }
        return phaseThreads;
    }

    private void writeMatrix()
            throws ServiceException {
        PhaseWriter phaseWriter = new PhaseWriter();
        phaseWriter.writeMatrix(matrix);
    }

}

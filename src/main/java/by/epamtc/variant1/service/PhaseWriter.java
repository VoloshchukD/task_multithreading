package by.epamtc.variant1.service;

import by.epamtc.variant1.dao.PhaseChangeDao;
import by.epamtc.variant1.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.DaoException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhaseWriter {

    private static final Logger logger = LogManager.getLogger();

    private static final String PHASE_TEXT = "////PHASE////";

    private static final String TEXT_SEPARATOR = " ";

    private static final String THREAD_TEXT = "thread ";

    private static final String SUM_TEXT = "result sum = ";

    public PhaseWriter() {
    }

    public void writeResult(Matrix matrix, Map<Integer, Future<Integer>> data) {
        String result = prepareData(matrix, data);
        PhaseChangeDao matrixChangeDao = PhaseChangeDaoImpl.getInstance();
        try {
            matrixChangeDao.writeMatrixChange(result);
        } catch (DaoException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        System.out.println("///////////////PHASEEEE/////////////");
    }

    private String prepareData(Matrix matrix, Map<Integer, Future<Integer>> data) {
        StringBuilder stringBuilder = new StringBuilder(PHASE_TEXT);
        stringBuilder.append("\n");
        try {
            for (Map.Entry<Integer, Future<Integer>> entry : data.entrySet()) {
                stringBuilder.append(THREAD_TEXT).append(entry.getKey()).append(TEXT_SEPARATOR);
                stringBuilder.append(SUM_TEXT).append(entry.getValue().get());
                stringBuilder.append("\n");
            }
            stringBuilder.append(matrix);
            stringBuilder.append("\n");
        } catch (ExecutionException | InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return stringBuilder.toString();
    }

}

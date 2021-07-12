package by.epamtc.variant2.service;

import by.epamtc.variant2.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.variant2.entity.Matrix;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class PhaseWriter {

    private static PhaseWriter instance;

    private static volatile boolean created = false;

    private static final Logger logger = LogManager.getLogger();

    private static final String PHASE_TEXT = "////PHASE////";

    private static final String TEXT_SEPARATOR = " ";

    private static final String THREAD_TEXT = "thread ";

    private static final String SUM_TEXT = "result sum = ";

    private PhaseWriter() {
    }

    public static PhaseWriter getInstance() {
        if (!created) {
            synchronized (PhaseWriter.class) {
                if (instance == null) {
                    instance = new PhaseWriter();
                    created = true;
                }
            }
        }
        return instance;
    }

    public void writeResult(Matrix matrix, Map<Integer, Integer> data) {
        String result = prepareData(matrix, data);
        PhaseChangeDaoImpl matrixChangeDao = PhaseChangeDaoImpl.getInstance();
        try {
            matrixChangeDao.writeMatrixChange(result);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        System.out.println("///////////////PHASEEEE/////////////");
    }

    private synchronized String prepareData(Matrix matrix, Map<Integer, Integer> data) {
        StringBuilder stringBuilder = new StringBuilder(PHASE_TEXT);
        stringBuilder.append("\n");
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            stringBuilder.append(THREAD_TEXT).append(entry.getKey()).append(TEXT_SEPARATOR);
            stringBuilder.append(SUM_TEXT).append(entry.getValue());
            stringBuilder.append("\n");
        }
        stringBuilder.append(matrix);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

}

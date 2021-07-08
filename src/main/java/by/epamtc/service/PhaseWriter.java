package by.epamtc.service;

import by.epamtc.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.entity.Element;
import by.epamtc.entity.Matrix;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhaseWriter {

    private static PhaseWriter instance;

    private static Lock lock = new ReentrantLock();

    private static AtomicBoolean created = new AtomicBoolean(false);

    private static final Logger logger = LogManager.getLogger();

    private static final String PHASE_TEXT = "////PHASE////";

    private static final String TEXT_SEPARATOR = " ";

    private static final String THREAD_TEXT = "thread ";

    private static final String SUM_TEXT = "result sum = ";

    private PhaseWriter() {
    }

    public static PhaseWriter getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new PhaseWriter();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void writeResult(Matrix matrix, Map<Integer, Future<Integer>> data) {
        lock.lock();
        String result = prepareData(matrix, data);
        PhaseChangeDaoImpl matrixChangeDao = new PhaseChangeDaoImpl();
        try {
            matrixChangeDao.writeMatrixChange(result);
        } catch (IOException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        System.out.println("///////////////PHASEEEE/////////////");
        lock.unlock();
    }

    private String prepareData(Matrix matrix, Map<Integer, Future<Integer>> data) {
        StringBuilder stringBuilder = new StringBuilder(PHASE_TEXT);
        stringBuilder.append("\n");
        stringBuilder.append(matrix);
        try {
            for (Map.Entry<Integer, Future<Integer>> entry : data.entrySet()) {
                stringBuilder.append(THREAD_TEXT).append(entry.getKey()).append(TEXT_SEPARATOR);
                stringBuilder.append(SUM_TEXT).append(entry.getValue().get());
                stringBuilder.append("\n");
            }
            stringBuilder.append("\n");
        } catch (ExecutionException | InterruptedException e) {
            logger.log(Level.ERROR, e.getMessage());
        }
        return stringBuilder.toString();
    }

}

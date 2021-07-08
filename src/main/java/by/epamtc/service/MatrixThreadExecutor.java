package by.epamtc.service;

import by.epamtc.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.dao.impl.MatrixDaoImpl;
import by.epamtc.entity.EditData;
import by.epamtc.entity.Matrix;
import by.epamtc.service.thread.MatrixThread;

import javax.xml.bind.JAXBException;
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
        phaseWriter = PhaseWriter.getInstance();
    }

    public void run() {
        int currentThreadIndex = 0;
        for (int i = 0; i < (editData.length / editsPerPhase); i++) {
            Map<Integer, Future<Integer>> map = new LinkedHashMap<>();
            CyclicBarrier barrier = new CyclicBarrier(editsPerPhase);
            for (int j = 0; j < editsPerPhase; j++, currentThreadIndex++) {
                MatrixThread matrixThread = new MatrixThread(j + 1, matrix, editData[currentThreadIndex], barrier);
                Future<Integer> future = executorService.submit(matrixThread);
                map.put(j + 1, future);
            }
            saveMatrix(matrix);
            phaseWriter.writeResult(matrix, map);
        }

    }

    public void saveMatrix(Matrix matrix) {
        MatrixDaoImpl matrixDao = new MatrixDaoImpl();
        try {
            matrixDao.writeMatrix(matrix);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}

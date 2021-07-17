package by.epamtc.variant1.service;

import by.epamtc.variant1.dao.PhaseChangeDao;
import by.epamtc.variant1.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.DaoException;
import by.epamtc.variant1.exception.ServiceException;

import java.util.Map;

public class PhaseWriter {

    private static final String PHASE_TEXT = "////PHASE////";

    private static final String TEXT_SEPARATOR = " ";

    private static final String THREAD_TEXT = "thread ";

    private static final String SUM_TEXT = "result sum = ";

    public void writeResult(Matrix matrix, Map<Integer, Integer> data) throws ServiceException {
        String result = makeString(matrix, data);
        PhaseChangeDao matrixChangeDao = PhaseChangeDaoImpl.getInstance();
        try {
            matrixChangeDao.writeMatrixChange(result);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private String makeString(Matrix matrix, Map<Integer, Integer> data) {
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

package by.epamtc.variant1.service;

import by.epamtc.variant1.dao.PhaseChangeDao;
import by.epamtc.variant1.dao.impl.PhaseChangeDaoImpl;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.DaoException;
import by.epamtc.variant1.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PhaseWriter {

    private static final String PHASE_TEXT = "PHASE END";

    private static final String TEXT_SEPARATOR = " ";

    private static final String THREAD_TEXT = "thread ";

    private static final String SUM_TEXT = "result sum = ";

    private static final Logger logger = LogManager.getLogger();

    public void writeEditResult(EditData editData, Integer sumResult) throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(THREAD_TEXT).append(editData.getThreadId()).append(TEXT_SEPARATOR);
            stringBuilder.append(SUM_TEXT).append(sumResult).append(TEXT_SEPARATOR);
            stringBuilder.append(editData.toString());
            stringBuilder.append("\n");
        PhaseChangeDao matrixChangeDao = PhaseChangeDaoImpl.getInstance();
        try {
            matrixChangeDao.writeText(stringBuilder.toString());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.log(Level.INFO, "Edit by thread write");
    }

    public void writeMatrix(Matrix matrix) throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder(matrix.toString());
        stringBuilder.append(PHASE_TEXT);
        stringBuilder.append("\n");
        PhaseChangeDao matrixChangeDao = PhaseChangeDaoImpl.getInstance();
        try {
            matrixChangeDao.writeText(stringBuilder.toString());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        logger.log(Level.INFO, "Matrix changes write");
    }

}

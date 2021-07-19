package by.epamtc.variant2.run;

import by.epamtc.variant1.exception.ServiceException;
import by.epamtc.variant2.dao.EditDataDao;
import by.epamtc.variant2.dao.MatrixDao;
import by.epamtc.variant2.dao.impl.EditDataDaoImpl;
import by.epamtc.variant2.dao.impl.MatrixDaoImpl;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.exception.DaoException;
import by.epamtc.variant2.service.MatrixService;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Runner {
    public static void main(String[] args) {
        Matrix matrix = null;
        MatrixDao matrixDao = MatrixDaoImpl.getInstance();
        try {
            matrix = matrixDao.readMatrix();
        } catch (DaoException e) {
            e.printStackTrace();
        }

        EditDataDao editDataDao = EditDataDaoImpl.getInstance();
        List<EditData> list = null;
        int phaseNumber = 3;
        try {
            list = editDataDao.readAllEditData(matrix.size() * phaseNumber);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        Queue<EditData> editData = new ArrayDeque<>(list);

        MatrixService matrixService = new MatrixService(matrix.size(), matrix, editData);
        try {
            matrixService.run();
        } catch (InterruptedException | ServiceException e) {
            e.printStackTrace();
        }

    }

}

package by.epamtc.variant1.run;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.dao.MatrixDao;
import by.epamtc.variant1.dao.impl.EditDataDaoImpl;
import by.epamtc.variant1.dao.impl.MatrixDaoImpl;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.DaoException;
import by.epamtc.variant1.exception.ServiceException;
import by.epamtc.variant1.service.MatrixService;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutionException;

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
        int phaseNumber = 3;
        List<EditData> editDataList = null;
        try {
            editDataList = editDataDao.readAllEditData(phaseNumber * matrix.size());
        } catch (DaoException e) {
            e.printStackTrace();
        }
        Queue<EditData> editData = new ArrayDeque<>(editDataList);

        MatrixService matrixService = new MatrixService(matrix, editData, matrix.size());
        try {
            matrixService.execute();
        } catch (InterruptedException | ExecutionException | ServiceException e) {
            e.printStackTrace();
        }

    }

}

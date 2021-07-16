package by.epamtc.variant2.run;

import by.epamtc.variant2.dao.EditDataDao;
import by.epamtc.variant2.dao.MatrixDao;
import by.epamtc.variant2.dao.impl.EditDataDaoImpl;
import by.epamtc.variant2.dao.impl.MatrixDaoImpl;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.exception.DaoException;
import by.epamtc.variant2.service.MatrixThreadExecutor;

import java.util.ArrayDeque;
import java.util.Queue;

public class Runner {
    public static void main(String[] args) {
//
//        int matrixSize = 4;
//        int[][] values = new int[matrixSize][matrixSize];
//        for (int i = 0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                values[i][i] = 0;
//            }
//        }
//        Matrix matrix = new Matrix(values);

        MatrixDao matrixDao = MatrixDaoImpl.getInstance();
//        try {
//            matrixDao.writeMatrix(matrix);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Matrix matrix = null;
        try {
            matrix = matrixDao.readMatrix();
        } catch (DaoException e) {
            e.printStackTrace();
        }


        Queue<EditData> editData = new ArrayDeque<>();
        for (int i = 0; i < matrix.size() * 3; i++) {
            EditData data = new EditData();
            data.setMutableIndex(random(matrix.size()));
            data.setDiagonalIndex(random(matrix.size()));
            data.setRowMutable(random(1) == 1);
            data.setNewElement(random(99));
            data.setThreadId(random(99));
            editData.offer(data);
        }


        EditDataDao editDataDao = EditDataDaoImpl.getInstance();
//        try {
//            editDataDao.writeAllEditData(editData);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        Queue<EditData> editData = new ArrayDeque<>();
//        try {
//            editData = editDataDao.readAllEditData(matrix.size() * 3);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrix.size(), matrix, editData);
        try {
            matrixThreadExecutor.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int random(int upperBorder) {
        return 0 + (int) (Math.random() * upperBorder);
    }

}

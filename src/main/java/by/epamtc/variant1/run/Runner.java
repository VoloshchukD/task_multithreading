package by.epamtc.variant1.run;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.dao.MatrixDao;
import by.epamtc.variant1.dao.impl.EditDataDaoImpl;
import by.epamtc.variant1.dao.impl.MatrixDaoImpl;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.service.MatrixThreadExecutor;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) {
        MatrixDao matrixDao = new MatrixDaoImpl();
//        int matrixSize = 4;
//        int[][] values = new int[matrixSize][matrixSize];
//        for (int i = 0; i < matrixSize; i++) {
//            for (int j = 0; j < matrixSize; j++) {
//                values[i][i] = 0;
//            }
//        }
        Matrix matrix = null;
        try {
            matrix = matrixDao.readMatrix();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("read " + matrix);
        EditDataDao editDataDao = new EditDataDaoImpl();
        EditData[] editData = new EditData[matrix.size() * 3];
        try {
            editData = editDataDao.readAllEditData(editData.length);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
//        for (int i = 0; i < editData.length; i++) {
//            EditData data = new EditData();
//            data.setMutableIndex(random(matrix.size()));
//            data.setDiagonalIndex(random(matrix.size()));
//            data.setRowMutable(random(1) == 1);
//            data.setNewElement(random(99));
//            data.setThreadId(random(99));
//            editData[i] = data;
//        }

//        try {
//            editDataDao.writeAllEditData(editData);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrix, editData, matrix.size());
        matrixThreadExecutor.run();

    }

    public static int random(int upperBorder) {
        return 0 + (int) (Math.random() * upperBorder);
    }

}

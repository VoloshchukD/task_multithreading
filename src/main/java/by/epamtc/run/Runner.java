package by.epamtc.run;

import by.epamtc.dao.MatrixDao;
import by.epamtc.dao.impl.MatrixDaoImpl;
import by.epamtc.entity.EditData;
import by.epamtc.entity.Matrix;
import by.epamtc.service.MatrixThreadExecutor;

import java.util.concurrent.ExecutionException;

public class Runner {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        MatrixDao matrixDao = new MatrixDaoImpl();
        int matrixSize = 4;
        int[][] values = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                values[i][i] = 0;
            }
        }
        Matrix matrix = new Matrix(values);
//        try {
//            matrix = matrixDao.readMatrix();
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }

        EditData[] editData = new EditData[matrix.size() * 3];
        for (int i = 0; i < editData.length; i++) {
            EditData data = new EditData();
            data.setMutableIndex(random(matrix.size()));
            data.setDiagonalIndex(random(matrix.size()));
            data.setRowMutable(random(1) == 1);
            data.setNewElement(random(99));
            data.setThreadId(random(99));
            editData[i] = data;
        }

        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrix, editData, matrix.size());
        matrixThreadExecutor.run();

    }

    public static int random(int upperBorder) {
        return 0 + (int) (Math.random() * upperBorder);
    }

}

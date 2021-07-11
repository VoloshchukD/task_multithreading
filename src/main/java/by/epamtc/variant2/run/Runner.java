package by.epamtc.variant2.run;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.dao.impl.EditDataDaoImpl;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.service.MatrixThreadExecutor;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws InterruptedException {

        int matrixSize = 4;
        int[][] values = new int[matrixSize][matrixSize];
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                values[i][i] = 0;
            }
        }
        Matrix matrix = new Matrix(values);


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


        MatrixThreadExecutor matrixThreadExecutor = new MatrixThreadExecutor(matrixSize, matrix, editData);
        matrixThreadExecutor.run();
    }

    public static int random(int upperBorder) {
        return 0 + (int) (Math.random() * upperBorder);
    }

}

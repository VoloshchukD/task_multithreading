package by.epamtc.service.thread;

import by.epamtc.dao.impl.MatrixChangeDaoImpl;
import by.epamtc.dao.impl.MatrixDaoImpl;
import by.epamtc.entity.Matrix;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Map;

public class WriterThread extends Thread {

    private Matrix matrix;

    private Map<Integer, Integer> data;

    public WriterThread(Matrix matrix, Map<Integer, Integer> data) {
        this.matrix = matrix;
        this.data = data;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Map<Integer, Integer> getData() {
        return data;
    }

    public void setData(Map<Integer, Integer> data) {
        this.data = data;
    }

    @Override
    public void run() {
        MatrixDaoImpl matrixDao = new MatrixDaoImpl();
        try {
            matrixDao.writeMatrix(matrix);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("...write... - " + data.toString());

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            stringBuilder.append("thread ").append(entry.getKey()).append(" ");
            stringBuilder.append("result sum ").append(entry.getValue());
        }
        System.out.println();
        stringBuilder.append("\n");
        stringBuilder.append(matrix);
        MatrixChangeDaoImpl matrixChangeDao = new MatrixChangeDaoImpl();
        try {
            matrixChangeDao.writeMatrixChange(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

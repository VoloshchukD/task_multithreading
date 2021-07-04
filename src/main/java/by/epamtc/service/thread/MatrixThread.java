package by.epamtc.service.thread;

import by.epamtc.entity.Matrix;

import java.util.concurrent.Callable;

public class MatrixThread implements Callable<Integer> {

    private int threadId;

    private Matrix matrix;

    private int diagonalIndex;

    private int mutableIndex;

    private boolean isRowMutableIndex;
    public MatrixThread(int threadId, int diagonalIndex, int mutableIndex, boolean isRowMutableIndex) {
        this.matrix = Matrix.getInstance();
        this.threadId = threadId;
        this.diagonalIndex = diagonalIndex;
        this.mutableIndex = mutableIndex;
        this.isRowMutableIndex = isRowMutableIndex;
    }

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().setName((Integer.toString(threadId)));
        System.out.println("Start "  + Thread.currentThread().getName());
        addDiagonalElement();
        editElement();
        int resultSum = countSum();
        System.out.println("End "  + Thread.currentThread().getName());
        matrix.getElement(diagonalIndex, diagonalIndex).unlock();
        return resultSum;
    }

    private void addDiagonalElement() {
        matrix.getElement(diagonalIndex, diagonalIndex).changeValue(threadId);
    }

    private void editElement() {
        int rowIndex = diagonalIndex;
        int columnIndex = diagonalIndex;
        if (isRowMutableIndex) {
            rowIndex = mutableIndex;
        } else {
            columnIndex = mutableIndex;
        }
        matrix.getElement(rowIndex, columnIndex).changeValue(threadId);
    }

    private int countSum() {
        int sum = 0;
        for (int i = 0; i < matrix.size(); i++) {
            if (i != diagonalIndex) {
                sum += matrix.getElement(i, diagonalIndex).getValue();
                sum += matrix.getElement(diagonalIndex, i).getValue();
            }
        }
        sum += matrix.getElement(diagonalIndex, diagonalIndex).getValue();
        return sum;
    }

}
package by.epamtc.service;

import by.epamtc.entity.Matrix;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MatrixAction {

    private Matrix matrix;

    private int diagonalIndex;

    private int mutableIndex;

    private boolean isRowMutableIndex;

    public MatrixAction(Matrix matrix, int diagonalIndex, int mutableIndex, boolean isRowMutableIndex) {
        this.matrix = matrix;
        this.diagonalIndex = diagonalIndex;
        this.mutableIndex = mutableIndex;
        this.isRowMutableIndex = isRowMutableIndex;
    }

    public int proceed() {
        Lock lock = new ReentrantLock();
        lock.lock();
        System.out.println("Start "  + Thread.currentThread().getName());
        addDiagonalElement();
        editElement();
        int resultSum = countSum();
        System.out.println("End "  + Thread.currentThread().getName());
        lock.unlock();
        return resultSum;
    }

    private boolean addDiagonalElement() {
        String threadName = Thread.currentThread().getName();
        return matrix.addElement(Integer.parseInt(threadName), diagonalIndex, diagonalIndex);
    }

    private boolean editElement() {
        int rowIndex = diagonalIndex;
        int columnIndex = diagonalIndex;
        if (isRowMutableIndex) {
            rowIndex = mutableIndex;
        } else {
            columnIndex = mutableIndex;
        }
        String threadName = Thread.currentThread().getName();
        return matrix.addElement(Integer.parseInt(threadName), rowIndex, columnIndex);
    }

    private int countSum() {
        int sum = 0;
        for (int i = 0; i < matrix.size(); i++) {
            if (i != diagonalIndex) {
                sum += matrix.getElement(i, diagonalIndex);
                sum += matrix.getElement(diagonalIndex, i);
            }
        }
        sum += matrix.getElement(diagonalIndex, diagonalIndex);
        return sum;
    }

}

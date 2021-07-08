package by.epamtc.service.thread;

import by.epamtc.entity.EditData;
import by.epamtc.entity.Matrix;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class MatrixThread implements Callable<Integer> {

    private int threadId;

    private Matrix matrix;

    private EditData editData;

    private CyclicBarrier barrier;

    public MatrixThread(int threadId, Matrix matrix, EditData editData, CyclicBarrier barrier) {
        this.threadId = threadId;
        this.matrix = matrix;
        this.editData = editData;
        this.barrier = barrier;
    }

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().setName((Integer.toString(threadId)));
        System.out.println("Start " + Thread.currentThread().getName());
        addDiagonalElement();
        editElement();
        int resultSum = countSum();
        System.out.println("End " + Thread.currentThread().getName());
        matrix.getElement(editData.getDiagonalIndex(), editData.getDiagonalIndex()).unlock();
        barrier.await();
        return resultSum;
    }

    private void addDiagonalElement() {
        matrix.getElement(editData.getDiagonalIndex(), editData.getDiagonalIndex()).changeValue(threadId);
    }

    private void editElement() {
        int rowIndex = editData.getDiagonalIndex();
        int columnIndex = editData.getDiagonalIndex();
        if (editData.isRowMutable()) {
            rowIndex = editData.getMutableIndex();
        } else {
            columnIndex = editData.getMutableIndex();
        }
        matrix.getElement(rowIndex, columnIndex).changeValue(threadId);
    }

    private int countSum() {
        int sum = 0;
        for (int i = 0; i < matrix.size(); i++) {
            if (i != editData.getDiagonalIndex()) {
                sum += matrix.getElement(i, editData.getDiagonalIndex()).getValue();
                sum += matrix.getElement(editData.getDiagonalIndex(), i).getValue();
            }
        }
        sum += matrix.getElement(editData.getDiagonalIndex(), editData.getDiagonalIndex()).getValue();
        return sum;
    }

}

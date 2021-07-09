package by.epamtc.entity.thread;

import by.epamtc.entity.EditData;
import by.epamtc.entity.Matrix;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class MatrixThread implements Callable<Integer> {

    private Matrix matrix;

    private EditData editData;

    private CyclicBarrier barrier;

    public MatrixThread(Matrix matrix, EditData editData, CyclicBarrier barrier) {
        this.matrix = matrix;
        this.editData = editData;
        this.barrier = barrier;
    }

    public int getThreadId() {
        return editData.getThreadId();
    }

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
        matrix.lock();
        System.out.println("Start " + Thread.currentThread().getName());
        System.out.println(editData);
        addDiagonalElement();
        editElement();
        int resultSum = countSum();
        System.out.println("End " + Thread.currentThread().getName());
        matrix.unlock();
        barrier.await();
        return resultSum;
    }

    private void addDiagonalElement() throws InterruptedException {
        matrix.changeValue(editData.getDiagonalIndex(), editData.getDiagonalIndex(), editData.getThreadId());
    }

    private void editElement() throws InterruptedException {
        int rowIndex = editData.getDiagonalIndex();
        int columnIndex = editData.getDiagonalIndex();
        if (editData.isRowMutable()) {
            columnIndex = editData.getMutableIndex();
        } else {
            rowIndex = editData.getMutableIndex();
        }
        matrix.changeValue(rowIndex, columnIndex, editData.getNewElement());
    }

    private int countSum() throws InterruptedException {
        int sum = 0;
        for (int i = 0; i < matrix.size(); i++) {
            if (i != editData.getDiagonalIndex()) {
                sum += matrix.getElement(i, editData.getDiagonalIndex());
                sum += matrix.getElement(editData.getDiagonalIndex(), i);
            }
        }
        sum += matrix.getElement(editData.getDiagonalIndex(), editData.getDiagonalIndex());
        return sum;
    }

}

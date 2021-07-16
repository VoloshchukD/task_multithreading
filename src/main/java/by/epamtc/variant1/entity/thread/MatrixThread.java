package by.epamtc.variant1.entity.thread;

import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;

import java.util.concurrent.Callable;

public class MatrixThread implements Callable<Integer> {

    private Matrix matrix;

    private EditData editData;

    public MatrixThread(Matrix matrix, EditData editData) {
        this.matrix = matrix;
        this.editData = editData;
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
        System.out.println("End " + Thread.currentThread().getName() + "; resSum " + resultSum);
        matrix.unlock();
        return resultSum;
    }

    private void addDiagonalElement() {
        matrix.changeValue(editData.getDiagonalIndex(), editData.getDiagonalIndex(), editData.getThreadId());
    }

    private void editElement() {
        int rowIndex = editData.getDiagonalIndex();
        int columnIndex = editData.getDiagonalIndex();
        if (editData.isRowMutable()) {
            columnIndex = editData.getMutableIndex();
        } else {
            rowIndex = editData.getMutableIndex();
        }
        matrix.changeValue(rowIndex, columnIndex, editData.getNewElement());
    }

    private int countSum() {
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

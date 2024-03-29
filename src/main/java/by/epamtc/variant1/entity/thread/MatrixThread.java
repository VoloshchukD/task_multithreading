package by.epamtc.variant1.entity.thread;

import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.ServiceException;
import by.epamtc.variant1.service.PhaseWriter;

import java.util.concurrent.Callable;

public class MatrixThread implements Callable<Integer> {

    private Matrix matrix;

    private EditData editData;

    public MatrixThread(Matrix matrix, EditData editData) {
        this.matrix = matrix;
        this.editData = editData;
    }

    @Override
    public Integer call() throws Exception {
        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
        matrix.lock();
        addDiagonalElement();
        editElement();
        int resultSum = countSum();
        saveResult(resultSum);
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

    private void saveResult(int sumResult) throws ServiceException {
        PhaseWriter phaseWriter = new PhaseWriter();
        phaseWriter.writeEditResult(editData, sumResult);
    }

}

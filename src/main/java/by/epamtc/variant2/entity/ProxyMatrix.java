package by.epamtc.variant2.entity;

import by.epamtc.variant2.exception.ServiceException;
import by.epamtc.variant2.service.PhaseWriter;

public class ProxyMatrix {

    private Matrix matrix;

    public ProxyMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Matrix getMatrix() {
        return (Matrix) matrix.clone();
    }

    public int getSize() {
        return matrix.size();
    }

    public synchronized void editMatrix(EditData editData) throws ServiceException {
        addDiagonalElement(editData.getDiagonalIndex(), editData.getThreadId());
        editElement(editData.getDiagonalIndex(), editData.getMutableIndex(), editData.getNewElement(), editData.isRowMutable());
        int resultSum = countSum(editData.getDiagonalIndex());
        saveResult(resultSum, editData);
    }

    private void addDiagonalElement(int diagonalIndex, int newElement) {
        matrix.changeValue(diagonalIndex, diagonalIndex, newElement);
    }

    private void editElement(int diagonalIndex, int mutableIndex, int newElement, boolean rowMutable) {
        int rowIndex = diagonalIndex;
        int columnIndex = diagonalIndex;
        if (rowMutable) {
            columnIndex = mutableIndex;
        } else {
            rowIndex = mutableIndex;
        }
        matrix.changeValue(rowIndex, columnIndex, newElement);
    }

    private int countSum(int diagonalIndex) {
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

    private void saveResult(int sumResult, EditData editData)
            throws ServiceException {
        PhaseWriter phaseWriter = new PhaseWriter();
        phaseWriter.writeEditResult(editData, sumResult);
    }

    @Override
    public String toString() {
        return matrix.toString();
    }

}

package by.epamtc.variant2.entity;

import java.util.HashMap;
import java.util.Map;

public class ProxyMatrix {

    private Matrix matrix;

    private HashMap<Integer, Integer> sumResult = new HashMap<>();

    public ProxyMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public Matrix getMatrix() {
        return (Matrix) matrix.clone();
    }

    public int getSize() {
        return matrix.size();
    }

    public void resetSumResult() {
        sumResult.clear();
    }

    public synchronized int doAction(EditData editData) {
        System.out.println(Thread.currentThread().getName() + " Start");
        addDiagonalElement(editData.getDiagonalIndex(), editData.getThreadId());
        editElement(editData.getDiagonalIndex(), editData.getMutableIndex(), editData.getNewElement(), editData.isRowMutable());
        int resultSum = countSum(editData.getDiagonalIndex());
        System.out.println(Thread.currentThread().getName() + " Finished");
        sumResult.put(Integer.parseInt(Thread.currentThread().getName()), resultSum);
        System.out.println(matrix + "\n" + editData);
        return resultSum;
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

    @Override
    public String toString() {
        return matrix.toString();
    }

}

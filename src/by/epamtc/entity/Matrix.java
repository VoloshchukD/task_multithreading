package by.epamtc.entity;

public class Matrix {

    private int[][] values;

    public Matrix(int size) {
        this.values = new int[size][size];
    }

    public int getElement(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
    }

    public synchronized boolean addElement(int element, int rowIndex, int columnIndex) {
        boolean isAdded = false;
        if (rowIndex < values.length && columnIndex < values.length) {
            values[rowIndex][columnIndex] = element;
            isAdded = true;
        }
        return isAdded;
    }

    public boolean removeElement(int rowIndex, int columnIndex) {
        boolean isRemoved = false;
        if (rowIndex < values.length && columnIndex < values.length) {
            values[rowIndex][columnIndex] = 0;
            isRemoved = true;
        }
        return isRemoved;
    }

    public int size() {
        return values.length;
    }

    @Override
    public String toString() {
        StringBuilder stringValue = new StringBuilder(getClass().getName());
        stringValue.append("\n");
        for (int[] rowValues : values) {
            for (int value : rowValues) {
                stringValue.append(value);
                stringValue.append("  ");
            }
            stringValue.append("\n");
        }
        return stringValue.toString();
    }

}

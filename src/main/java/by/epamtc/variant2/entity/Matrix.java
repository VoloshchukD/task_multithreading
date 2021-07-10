package by.epamtc.variant2.entity;

import java.io.Serializable;

public class Matrix implements Serializable {

    private int[][] values;

    public Matrix(int[][] values) {
        this.values = values;
    }

    public void changeValue(int rowIndex, int columnIndex, int value) {
            values[rowIndex][columnIndex] = value;
            System.out.println("Thread "+Thread.currentThread().getName() + " changed");
    }

    public int getElement(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
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

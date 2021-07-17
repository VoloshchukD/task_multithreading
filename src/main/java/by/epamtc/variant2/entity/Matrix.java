package by.epamtc.variant2.entity;

import java.io.Serializable;

public class Matrix implements Serializable {

    private int[][] values;

    public Matrix(int[][] values) {
        this.values = values;
    }

    public void changeValue(int rowIndex, int columnIndex, int value) {
        values[rowIndex][columnIndex] = value;
    }

    public int getElement(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
    }

    public int size() {
        return values.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        if (values == matrix.values) return true;
        if (values == null || matrix.values == null) return false;
        if (values.length != matrix.values.length) return false;

        for (int i = 0; i < values.length; i++) {
            if (values[i].length != matrix.values[i].length) return false;
            for (int j = 0; j < values.length; j++) {
                if (values[i][j] != matrix.values[i][j]) return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int valuesHashcode = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                valuesHashcode += values[i][j];
            }
        }
        result = 37 * result + valuesHashcode;
        return result;
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

    @Override
    protected Object clone() {
        return new Matrix(values);
    }

}

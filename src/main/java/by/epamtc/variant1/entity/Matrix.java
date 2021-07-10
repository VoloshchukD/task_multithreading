package by.epamtc.variant1.entity;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class Matrix implements Serializable {

    private int[][] values;

    private final Semaphore semaphore = new Semaphore(1, true);

    public Matrix(int[][] values) {
        this.values = values;
    }

    public void lock() throws InterruptedException {
        semaphore.acquire();
    }

    public void changeValue(int rowIndex, int columnIndex, int value) throws InterruptedException {
            values[rowIndex][columnIndex] = value;
            System.out.println("Thread "+Thread.currentThread().getName() + " changed");
    }

    public void unlock() {
        semaphore.release();
    }

    public int getElement(int rowIndex, int columnIndex) throws InterruptedException {
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

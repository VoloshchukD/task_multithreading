package by.epamtc.entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Matrix {

    private static Matrix instance;

    private Element[][] values;

    private static Lock lock = new ReentrantLock();

    private static AtomicBoolean created = new AtomicBoolean(false);

    private Matrix() {
    }

    public static Matrix getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new Matrix();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void setValues(Element[][] values) {
        this.values = values;
    }

    public Element getElement(int rowIndex, int columnIndex) {
        return values[rowIndex][columnIndex];
    }

    public int size() {
        return values.length;
    }

    @Override
    public String toString() {
        StringBuilder stringValue = new StringBuilder(getClass().getName());
        stringValue.append("\n");
        for (Element[] rowValues : values) {
            for (Element value : rowValues) {
                stringValue.append(value);
                stringValue.append("  ");
            }
            stringValue.append("\n");
        }
        return stringValue.toString();
    }

}

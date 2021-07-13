package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.MatrixDao;
import by.epamtc.variant1.entity.Matrix;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MatrixDaoImpl implements MatrixDao {

    private static MatrixDaoImpl instance;

    private static Lock lock = new ReentrantLock();

    private static AtomicBoolean created = new AtomicBoolean(false);

    private static final String FILE_NAME = "data/variant1/matrix.txt";

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private MatrixDaoImpl() {
    }

    public static MatrixDaoImpl getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new MatrixDaoImpl();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Matrix readMatrix() throws IOException, ClassNotFoundException {
        readWriteLock.readLock().lock();
        ObjectInputStream objectInputStream = null;
        Matrix matrix = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            matrix = (Matrix) objectInputStream.readObject();
        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            readWriteLock.readLock().unlock();
        }
        return matrix;
    }

    @Override
    public void writeMatrix(Matrix matrix) throws IOException {
        readWriteLock.writeLock().lock();
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(matrix);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            readWriteLock.writeLock().unlock();
        }
    }

}

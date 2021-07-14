package by.epamtc.variant2.dao.impl;

import by.epamtc.variant2.exception.DaoException;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.dao.MatrixDao;

import java.io.*;

public class MatrixDaoImpl implements MatrixDao {

    private static MatrixDaoImpl instance;

    private static volatile boolean created = false;

    public static final String FILE_NAME = "data/variant2/matrix.txt";

    private MatrixDaoImpl() {
    }

    public static MatrixDaoImpl getInstance() {
        if (!created) {
            synchronized (MatrixDaoImpl.class) {
                if (instance == null) {
                    instance = new MatrixDaoImpl();
                    created = true;
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized Matrix readMatrix() throws DaoException {
        ObjectInputStream objectInputStream = null;
        Matrix matrix = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            matrix = (Matrix) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException(e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
        return matrix;
    }

    @Override
    public synchronized void writeMatrix(Matrix matrix) throws DaoException {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(matrix);
        } catch (IOException e) {
            throw new DaoException(e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
    }

}

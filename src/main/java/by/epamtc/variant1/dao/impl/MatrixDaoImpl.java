package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.MatrixDao;
import by.epamtc.variant1.entity.Matrix;

import java.io.*;

public class MatrixDaoImpl implements MatrixDao {

    public static final String FILE_NAME = "data/matrix.txt";

    @Override
    public Matrix readMatrix() throws IOException, ClassNotFoundException {
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
        }
        return matrix;
    }

    @Override
    public void writeMatrix(Matrix matrix) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(matrix);
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
    }

}

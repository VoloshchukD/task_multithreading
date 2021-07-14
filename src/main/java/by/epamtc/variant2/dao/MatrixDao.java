package by.epamtc.variant2.dao;

import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.exception.DaoException;

import java.io.IOException;

public interface MatrixDao {

    Matrix readMatrix() throws DaoException;

    void writeMatrix(Matrix matrix) throws DaoException;

}

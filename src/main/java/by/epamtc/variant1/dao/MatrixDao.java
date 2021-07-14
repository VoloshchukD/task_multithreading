package by.epamtc.variant1.dao;

import by.epamtc.variant1.entity.Matrix;
import by.epamtc.variant1.exception.DaoException;

import java.io.IOException;

public interface MatrixDao {

   Matrix readMatrix() throws DaoException;

   void writeMatrix(Matrix matrix) throws DaoException;

}

package by.epamtc.variant2.dao;

import by.epamtc.variant2.entity.Matrix;

import java.io.IOException;

public interface MatrixDao {

   Matrix readMatrix() throws IOException, ClassNotFoundException;

   void writeMatrix(Matrix matrix) throws IOException;

}

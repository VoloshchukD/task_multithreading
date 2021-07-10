package by.epamtc.variant1.dao;

import by.epamtc.variant1.entity.Matrix;

import java.io.IOException;

public interface MatrixDao {

   Matrix readMatrix() throws IOException, ClassNotFoundException;

   void writeMatrix(Matrix matrix) throws IOException;

}

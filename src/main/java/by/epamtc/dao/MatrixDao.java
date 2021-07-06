package by.epamtc.dao;

import by.epamtc.entity.Matrix;

import javax.xml.bind.JAXBException;

public interface MatrixDao {

   Matrix readMatrix();

   void writeMatrix(Matrix matrix) throws JAXBException;

}

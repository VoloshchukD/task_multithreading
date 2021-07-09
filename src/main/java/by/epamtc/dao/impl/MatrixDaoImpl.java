package by.epamtc.dao.impl;

import by.epamtc.dao.MatrixDao;
import by.epamtc.entity.Matrix;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class MatrixDaoImpl implements MatrixDao {

    public static final String FILE_NAME = "data/matrix.xml";

    @Override
    public Matrix readMatrix() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Matrix.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(FILE_NAME);
        Matrix matrix = (Matrix) jaxbUnmarshaller.unmarshal(file);
        return matrix;
    }

    @Override
    public void writeMatrix(Matrix matrix) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Matrix.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File file = new File(FILE_NAME);
        jaxbMarshaller.marshal(matrix, file);
    }

}

package by.epamtc.variant1.dao;

import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.exception.DaoException;

import java.util.List;

public interface EditDataDao {

    EditData readEditData() throws DaoException;

    List<EditData> readAllEditData(int quantity) throws DaoException;

    boolean writeEditData(EditData editData) throws DaoException;

    boolean writeAllEditData(List<EditData> editData) throws DaoException;

}

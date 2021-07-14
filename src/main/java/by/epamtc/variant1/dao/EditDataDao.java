package by.epamtc.variant1.dao;

import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.exception.DaoException;

public interface EditDataDao {

    EditData readEditData() throws DaoException;

    EditData[] readAllEditData(int quantity) throws DaoException;

    boolean writeEditData(EditData editData) throws DaoException;

    boolean writeAllEditData(EditData[] editData) throws DaoException;

}

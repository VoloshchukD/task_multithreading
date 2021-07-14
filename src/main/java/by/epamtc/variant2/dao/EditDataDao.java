package by.epamtc.variant2.dao;

import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.exception.DaoException;

public interface EditDataDao {

    EditData readEditData() throws DaoException;

    EditData[] readAllEditData(int quantity) throws DaoException;

    boolean writeEditData(EditData editData) throws DaoException;

    boolean writeAllEditData(EditData[] editData) throws DaoException;

}

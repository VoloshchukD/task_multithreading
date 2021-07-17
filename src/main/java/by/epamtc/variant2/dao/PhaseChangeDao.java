package by.epamtc.variant2.dao;

import by.epamtc.variant2.exception.DaoException;

public interface PhaseChangeDao {

    void writeMatrixChange(String text) throws DaoException;

}

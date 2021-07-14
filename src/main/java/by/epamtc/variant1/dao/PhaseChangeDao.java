package by.epamtc.variant1.dao;

import by.epamtc.variant1.exception.DaoException;

import java.io.IOException;

public interface PhaseChangeDao {

    void writeMatrixChange(String text) throws DaoException;

}

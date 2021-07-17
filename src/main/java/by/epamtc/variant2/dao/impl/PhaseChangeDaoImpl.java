package by.epamtc.variant2.dao.impl;

import by.epamtc.variant2.dao.PhaseChangeDao;
import by.epamtc.variant2.exception.DaoException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PhaseChangeDaoImpl implements PhaseChangeDao {

    private static PhaseChangeDaoImpl instance;

    private static volatile boolean created = false;

    public static final String FILE_NAME = "data/variant2/result.txt";

    private PhaseChangeDaoImpl() {
    }

    public static PhaseChangeDaoImpl getInstance() {
        if (!created) {
            synchronized (PhaseChangeDaoImpl.class) {
                if (instance == null) {
                    instance = new PhaseChangeDaoImpl();
                    created = true;
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized void writeMatrixChange(String text) throws DaoException {
        File file = new File(FILE_NAME);
        BufferedWriter bufferedWriter = null;
        try {
            FileWriter fr = new FileWriter(file, true);
            bufferedWriter = new BufferedWriter(fr);
            bufferedWriter.write(text);
        } catch (IOException e) {
            throw new DaoException(e);
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                throw new DaoException(e);
            }
        }
    }

}

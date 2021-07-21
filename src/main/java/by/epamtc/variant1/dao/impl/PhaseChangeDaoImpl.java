package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.PhaseChangeDao;
import by.epamtc.variant1.exception.DaoException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PhaseChangeDaoImpl implements PhaseChangeDao {

    private static PhaseChangeDaoImpl instance;

    private static Lock lock = new ReentrantLock();

    private Lock writeLock = new ReentrantLock();

    private static AtomicBoolean created = new AtomicBoolean(false);

    private static final String FILE_NAME = "data/variant1/result.txt";

    private PhaseChangeDaoImpl() {
    }

    public static PhaseChangeDaoImpl getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new PhaseChangeDaoImpl();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public void writeText(String text) throws DaoException {
        writeLock.lock();
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
            writeLock.unlock();
        }
    }

}

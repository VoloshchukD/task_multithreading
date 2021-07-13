package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.PhaseChangeDao;

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

    private static AtomicBoolean created = new AtomicBoolean(false);

    private static final String FILE_NAME = "data/variant1/result.txt";

    private Lock writeLock = new ReentrantLock();

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
    public void writeMatrixChange(String text) throws IOException {
        writeLock.lock();
        File file = new File(FILE_NAME);
        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(text);
        } finally {
            br.close();
            fr.close();
            writeLock.unlock();
        }
    }

}

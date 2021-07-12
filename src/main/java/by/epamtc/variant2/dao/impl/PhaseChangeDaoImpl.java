package by.epamtc.variant2.dao.impl;

import by.epamtc.variant2.dao.PhaseChangeDao;

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
    public synchronized void writeMatrixChange(String text) throws IOException {
        File file = new File(FILE_NAME);
        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.PhaseChangeDao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PhaseChangeDaoImpl implements PhaseChangeDao {

    public static final String FILE_NAME = "data/result.txt";

    @Override
    public void writeMatrixChange(String text) throws IOException {
        File file = new File(FILE_NAME);
        FileWriter fr = null;
        BufferedWriter br = null;
        try{
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

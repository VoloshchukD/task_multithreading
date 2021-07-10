package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.entity.EditData;

import java.io.*;

public class EditDataDaoImpl implements EditDataDao {

    public static final String FILE_NAME = "data/edit.txt";

    @Override
    public EditData readEditData() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = null;
        EditData editData = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            editData = (EditData) objectInputStream.readObject();
        } finally {
            if (objectInputStream != null) {
                objectInputStream.close();
            }
        }
        return editData;
    }

    public EditData[] readAllEditData(int quantity) throws IOException, ClassNotFoundException {
        EditData[] editData = new EditData[quantity];
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            for (int i = 0; i < quantity; i++) {
                editData[i] = (EditData) inputStream.readObject();
            }
        }
        return editData;
    }

    @Override
    public boolean writeEditData(EditData editData) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        boolean saved = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(editData);
            saved = true;
        } finally {
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
        }
        return saved;
    }

    public boolean writeAllEditData(EditData[] editData) throws IOException {
        boolean saved = false;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (int i = 0; i < editData.length; i++) {
                outputStream.writeObject(editData[i]);
            }
            saved = true;
        }
        return saved;
    }

}

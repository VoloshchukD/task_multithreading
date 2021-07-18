package by.epamtc.variant2.dao.impl;

import by.epamtc.variant2.dao.EditDataDao;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.exception.DaoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EditDataDaoImpl implements EditDataDao {

    private static EditDataDaoImpl instance;

    private static volatile boolean created = false;

    public static final String FILE_NAME = "data/variant2/edit.txt";

    private EditDataDaoImpl() {
    }

    public static EditDataDaoImpl getInstance() {
        if (!created) {
            synchronized (EditDataDaoImpl.class) {
                if (instance == null) {
                    instance = new EditDataDaoImpl();
                    created = true;
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized EditData readEditData() throws DaoException {
        ObjectInputStream objectInputStream = null;
        EditData editData = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            editData = (EditData) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
        return editData;
    }

    public synchronized List<EditData> readAllEditData(int quantity) throws DaoException {
        List<EditData> editData = new ArrayList<>();
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            for (int i = 0; i < quantity; i++) {
                editData.add((EditData) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoException(e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
        return editData;
    }

    @Override
    public synchronized boolean writeEditData(EditData editData) throws DaoException {
        ObjectOutputStream objectOutputStream = null;
        boolean saved = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(editData);
            saved = true;
        } catch (IOException e) {
            throw new DaoException(e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
        return saved;
    }

    public synchronized boolean writeAllEditData(List<EditData> editData) throws DaoException {
        ObjectOutputStream objectOutputStream = null;
        boolean saved = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (int i = 0; i < editData.size(); i++) {
                objectOutputStream.writeObject(editData.get(i));
            }
            saved = true;
        } catch (IOException e) {
            throw new DaoException(e);
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
        }
        return saved;
    }

}

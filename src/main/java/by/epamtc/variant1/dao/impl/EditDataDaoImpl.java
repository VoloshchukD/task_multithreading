package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.entity.EditData;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EditDataDaoImpl implements EditDataDao {

    private static EditDataDaoImpl instance;

    private static Lock lock = new ReentrantLock();

    private static AtomicBoolean created = new AtomicBoolean(false);

    private static final String FILE_NAME = "data/variant1/edit.txt";

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private EditDataDaoImpl() {
    }

    public static EditDataDaoImpl getInstance() {
        if (!created.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new EditDataDaoImpl();
                    created.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public EditData readEditData() throws IOException, ClassNotFoundException {
        readWriteLock.readLock().lock();
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
            readWriteLock.readLock().unlock();
        }
        return editData;
    }

    public EditData[] readAllEditData(int quantity) throws IOException, ClassNotFoundException {
        readWriteLock.readLock().lock();
        EditData[] editData = new EditData[quantity];
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            for (int i = 0; i < quantity; i++) {
                editData[i] = (EditData) inputStream.readObject();
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return editData;
    }

    @Override
    public boolean writeEditData(EditData editData) throws IOException {
        readWriteLock.writeLock().lock();
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
            readWriteLock.writeLock().unlock();
        }
        return saved;
    }

    public boolean writeAllEditData(EditData[] editData) throws IOException {
        readWriteLock.writeLock().lock();
        boolean saved = false;
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            for (int i = 0; i < editData.length; i++) {
                outputStream.writeObject(editData[i]);
            }
            saved = true;
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return saved;
    }

}

package by.epamtc.variant1.dao.impl;

import by.epamtc.variant1.dao.EditDataDao;
import by.epamtc.variant1.entity.EditData;
import by.epamtc.variant1.exception.DaoException;

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
    public EditData readEditData() throws DaoException {
        readWriteLock.readLock().lock();
        ObjectInputStream objectInputStream = null;
        EditData editData = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            editData = (EditData) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new DaoException(e);
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    throw new DaoException(e);
                }
            }
            readWriteLock.readLock().unlock();
        }
        return editData;
    }

    public EditData[] readAllEditData(int quantity) throws DaoException {
        readWriteLock.readLock().lock();
        EditData[] editData = new EditData[quantity];
        ObjectInputStream objectInputStream = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
            objectInputStream = new ObjectInputStream(fileInputStream);
            for (int i = 0; i < quantity; i++) {
                editData[i] = (EditData) objectInputStream.readObject();
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
            readWriteLock.readLock().unlock();
        }
        return editData;
    }

    @Override
    public boolean writeEditData(EditData editData) throws DaoException {
        readWriteLock.writeLock().lock();
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
            readWriteLock.writeLock().unlock();
        }
        return saved;
    }

    public boolean writeAllEditData(EditData[] editData) throws DaoException {
        readWriteLock.writeLock().lock();
        ObjectOutputStream objectOutputStream = null;
        boolean saved = false;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (int i = 0; i < editData.length; i++) {
                objectOutputStream.writeObject(editData[i]);
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
            readWriteLock.writeLock().unlock();
        }
        return saved;
    }

}

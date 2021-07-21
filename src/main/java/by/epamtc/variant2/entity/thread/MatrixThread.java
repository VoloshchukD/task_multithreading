package by.epamtc.variant2.entity.thread;

import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.ProxyMatrix;
import by.epamtc.variant2.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MatrixThread extends Thread {

    private ProxyMatrix proxyMatrix;

    private EditData editData;

    private static final Logger logger = LogManager.getLogger();

    public MatrixThread(ProxyMatrix proxyMatrix, EditData editData) {
        this.proxyMatrix = proxyMatrix;
        this.editData = editData;
    }

    @Override
    public void run() {
        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
        try {
            proxyMatrix.doAction(editData);
        } catch (ServiceException e) {
            logger.log(Level.ERROR, e);
        }
    }

}

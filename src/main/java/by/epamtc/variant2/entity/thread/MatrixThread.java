package by.epamtc.variant2.entity.thread;

import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.ProxyMatrix;

public class MatrixThread extends Thread {

    private ProxyMatrix proxyMatrix;

    private EditData editData;

    private int sumResult;

    public MatrixThread(ProxyMatrix proxyMatrix, EditData editData) {
        this.proxyMatrix = proxyMatrix;
        this.editData = editData;
    }

    public int getSumResult() {
        return sumResult;
    }

    @Override
    public void run() {
        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
        System.out.println("Start " + Thread.currentThread().getName());
        sumResult = proxyMatrix.doAction(editData);
        System.out.println("End " + Thread.currentThread().getName());
    }

}

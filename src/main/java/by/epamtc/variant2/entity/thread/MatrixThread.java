package by.epamtc.variant2.entity.thread;

import by.epamtc.variant2.entity.CustomBarrier;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.ProxyMatrix;

public class MatrixThread extends Thread {

    private ProxyMatrix proxyMatrix;

    private EditData editData;

    private CustomBarrier barrier;

    public MatrixThread(ProxyMatrix proxyMatrix, EditData editData) {
        this.proxyMatrix = proxyMatrix;
        this.editData = editData;
        barrier = new CustomBarrier(proxyMatrix.getSize());
    }

    public void setBarrier(CustomBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
        System.out.println("Start " + Thread.currentThread().getName());
        try {
            proxyMatrix.doAction(editData);
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End " + Thread.currentThread().getName());

    }

}

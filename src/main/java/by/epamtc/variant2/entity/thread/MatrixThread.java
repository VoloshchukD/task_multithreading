package by.epamtc.variant2.entity.thread;

import by.epamtc.variant2.entity.CustomBarrier;
import by.epamtc.variant2.entity.EditData;
import by.epamtc.variant2.entity.Matrix;
import by.epamtc.variant2.entity.ProxyMatrix;

import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

public class MatrixThread extends Thread {

//    private ProxyMatrix matrix;
//
//    private EditData editData;
//
//    public MatrixThread(ProxyMatrix matrix, EditData editData) {
//        this.matrix = matrix;
//        this.editData = editData;
//    }
//
//    public int getThreadId() {
//        return editData.getThreadId();
//    }

    private CustomBarrier barrier;

    public CustomBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CustomBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        System.out.println("Start " + Thread.currentThread().getName());
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End " + Thread.currentThread().getName());

    }

    //    @Override
//    public Integer call() throws Exception {
//        Thread.currentThread().setName((Integer.toString(editData.getThreadId())));
//        matrix.lock();
//        System.out.println("Start " + Thread.currentThread().getName());
//        System.out.println(editData);
//        addDiagonalElement();
//        editElement();
//        int resultSum = countSum();
//        System.out.println("End " + Thread.currentThread().getName());
//        matrix.unlock();
//        barrier.await();
//        return resultSum;
//    }
//
//    private void addDiagonalElement() throws InterruptedException {
//        matrix.changeValue(editData.getDiagonalIndex(), editData.getDiagonalIndex(), editData.getThreadId());
//    }
//
//    private void editElement() throws InterruptedException {
//        int rowIndex = editData.getDiagonalIndex();
//        int columnIndex = editData.getDiagonalIndex();
//        if (editData.isRowMutable()) {
//            columnIndex = editData.getMutableIndex();
//        } else {
//            rowIndex = editData.getMutableIndex();
//        }
//        matrix.changeValue(rowIndex, columnIndex, editData.getNewElement());
//    }
//
//    private int countSum() throws InterruptedException {
//        int sum = 0;
//        for (int i = 0; i < matrix.size(); i++) {
//            if (i != editData.getDiagonalIndex()) {
//                sum += matrix.getElement(i, editData.getDiagonalIndex());
//                sum += matrix.getElement(editData.getDiagonalIndex(), i);
//            }
//        }
//        sum += matrix.getElement(editData.getDiagonalIndex(), editData.getDiagonalIndex());
//        return sum;
//    }

}

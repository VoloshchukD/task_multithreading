package by.epamtc.service.thread;

import java.util.ArrayList;
import java.util.concurrent.Future;

public class WriterThread extends Thread {

    private ArrayList<Future<Integer>> data;

    public WriterThread(ArrayList<Future<Integer>> data) {
        this.data = data;
    }

    @Override
    public void run() {
        System.out.println("...write... - " + data.toString());
//        for(Future<Integer> future :list) {
//            System.out.println(future.get() + " result fixed");
//        }
    }

}

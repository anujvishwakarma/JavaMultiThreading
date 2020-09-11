package com.cadmus.multithreading.ThreadPoolExecuter;

public class WorkerThread implements  Runnable{

    @Override
    public void run() {
        System.out.println("Working on Task : " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

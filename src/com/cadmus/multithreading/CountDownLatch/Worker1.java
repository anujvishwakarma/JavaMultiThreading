package com.cadmus.multithreading.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class Worker1 implements Runnable {

    private CountDownLatch countDownLatch;

    public Worker1(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Worker1 is working");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println("Worker1 is done");
    }
}

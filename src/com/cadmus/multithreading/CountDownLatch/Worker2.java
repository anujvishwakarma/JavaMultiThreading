package com.cadmus.multithreading.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class Worker2 implements Runnable{

    private CountDownLatch countDownLatch;

    public Worker2(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Worker2 is working");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println("Worker2 is done");
    }
}

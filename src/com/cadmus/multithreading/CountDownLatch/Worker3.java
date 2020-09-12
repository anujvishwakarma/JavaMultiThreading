package com.cadmus.multithreading.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class Worker3 implements Runnable{

    private CountDownLatch countDownLatch;

    public Worker3(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("Worker3 is waiting for worker1 and worker2");
        try {
            countDownLatch.await();
            System.out.println("Worker3 is working now");
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Worker3 is done");
    }
}

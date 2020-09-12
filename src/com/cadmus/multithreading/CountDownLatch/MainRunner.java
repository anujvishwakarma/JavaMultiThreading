package com.cadmus.multithreading.CountDownLatch;

import java.util.concurrent.CountDownLatch;

public class MainRunner {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Thread worker1 = new Thread(new Worker1(countDownLatch));
        Thread worker2 = new Thread(new Worker2(countDownLatch));
        Thread worker3 = new Thread(new Worker3(countDownLatch));

        worker1.start();
        worker2.start();
        worker3.start();
    }
}

package com.cadmus.multithreading.CyclicBarrier;

import java.util.concurrent.CyclicBarrier;

public class MainRunner {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier1 = new CyclicBarrier(2, new Barrier1());
        CyclicBarrier cyclicBarrier2 = new CyclicBarrier(2, new Barrier2());

        Thread worker1 = new Thread(new Worker(cyclicBarrier1, cyclicBarrier2, 3000, 7000));
        Thread worker2 = new Thread(new Worker(cyclicBarrier1, cyclicBarrier2, 7000, 3000));

        worker1.start();
        worker2.start();
    }
}

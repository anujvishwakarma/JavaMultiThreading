package com.cadmus.multithreading.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable{

    private CyclicBarrier barrier1;

    private CyclicBarrier barrier2;

    private int task1Time;
    private int task2Time;

    public Worker(CyclicBarrier barrier1, CyclicBarrier barrier2, int task1Time, int task2Time) {
        this.barrier1 = barrier1;
        this.barrier2 = barrier2;
        this.task1Time = task1Time;
        this.task2Time = task2Time;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" : worker is working" + "Task1");
        try {
            Thread.sleep(task1Time);
            System.out.println(Thread.currentThread().getName()+" : worker is done but waiting for other at barrier1");
            barrier1.await();
            System.out.println(Thread.currentThread().getName()+" : worker is working "+ "Task2");
            Thread.sleep(task2Time);
            System.out.println(Thread.currentThread().getName()+" : worker is done but waiting for other at barrier2");
            barrier2.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

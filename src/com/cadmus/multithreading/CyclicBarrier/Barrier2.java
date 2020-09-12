package com.cadmus.multithreading.CyclicBarrier;

public class Barrier2 implements Runnable{
    @Override
    public void run() {
        System.out.println("This is barrier 2");
    }
}

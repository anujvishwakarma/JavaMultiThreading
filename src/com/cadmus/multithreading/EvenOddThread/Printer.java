package com.cadmus.multithreading.EvenOddThread;

public class Printer {
    private volatile boolean isOdd = false;

    public synchronized void printEven(int counter){
        while (!isOdd){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+": "+ counter);
        isOdd = false;
        notify();
    }

    public synchronized void printOdd(int counter){
        while (isOdd){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+": "+ counter);
        isOdd = true;
        notify();
    }
}

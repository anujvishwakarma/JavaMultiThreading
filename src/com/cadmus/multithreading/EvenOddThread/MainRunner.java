package com.cadmus.multithreading.EvenOddThread;

public class MainRunner {
    public static void main(String[] args) {
        Printer printer = new Printer();

        Thread oddThread = new Thread(new TaskMaster(10, printer, true), "OddThread");
        Thread evenThread = new Thread(new TaskMaster(10, printer, false), "EvenThread");

        oddThread.start();
        evenThread.start();

    }
}

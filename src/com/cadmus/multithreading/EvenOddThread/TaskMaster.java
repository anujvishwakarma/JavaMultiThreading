package com.cadmus.multithreading.EvenOddThread;

public class TaskMaster implements Runnable{
    private int max;
    private Printer printer;
    private boolean isOdd;

    public TaskMaster(int max, Printer printer, boolean isOdd) {
        this.max = max;
        this.printer = printer;
        this.isOdd = isOdd;
    }

    @Override
    public void run() {
        int data = isOdd ? 1 : 2;
        while (data <= max){
            if(isOdd){
                printer.printOdd(data);
            }else{
                printer.printEven(data);
            }
            data+=2;
        }
    }
}

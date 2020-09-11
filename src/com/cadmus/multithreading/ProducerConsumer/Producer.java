package com.cadmus.multithreading.ProducerConsumer;

import java.util.Queue;

public class Producer implements Runnable{

    Queue<Integer> myQueue;

    private Integer maxSize;

    public Producer(Queue<Integer> myQueue, Integer maxSize) {
        this.myQueue = myQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        int data = 0;
        while (true){
            synchronized (myQueue){
                data++;
                while(myQueue.size() >= maxSize){
                    try {
                        myQueue.wait();
                        System.out.println("Waiting to consumed");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                myQueue.add(data);
                System.out.println("Data Produced is " + data);
                myQueue.notify();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

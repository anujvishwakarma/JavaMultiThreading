package com.cadmus.multithreading.ProducerConsumer;

import java.util.Queue;

public class Consumer implements Runnable{

    Queue<Integer> myQueue;

    private Integer maxSize;

    public Consumer(Queue<Integer> myQueue, Integer maxSize) {
        this.myQueue = myQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true){
            synchronized (myQueue){
                while (myQueue.size()==0){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int data = myQueue.remove();
                System.out.println("Consumed data is "+ data);
                myQueue.notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

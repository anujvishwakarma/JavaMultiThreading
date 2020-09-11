package com.cadmus.multithreading.MultipleConsumerProducer;

import java.util.Queue;

public class Producer implements Runnable{

    private static int data =0 ;

    Queue<Integer> myQueue;

    private Integer maxSize;

    public Producer(Queue<Integer> myQueue, Integer maxSize) {
        this.myQueue = myQueue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true){
            produce();
        }
    }

    private void produce(){
        synchronized (myQueue){
            while (myQueue.size() >= maxSize){
                try {
                    myQueue.wait();
                    System.out.println("Waiting to be consumed");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            data++;
            myQueue.add(data);
            System.out.println("Data Produced is " + data + " By Thread :" + Thread.currentThread().getName());
            try {
                Thread.sleep(((long) (Math.random() * 2000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myQueue.notifyAll();
        }
    }
}

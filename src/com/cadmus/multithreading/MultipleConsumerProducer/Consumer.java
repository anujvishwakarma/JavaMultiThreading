package com.cadmus.multithreading.MultipleConsumerProducer;

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
            consume();
        }
    }

    private void consume(){
        synchronized (myQueue){
            while (myQueue.size()==0){
                try {
                    myQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(((long) (Math.random() * 2000)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int data = myQueue.remove();
            System.out.println("Consumed data is "+ data + " By Thread :" + Thread.currentThread().getName());
            myQueue.notifyAll();
        }
    }
}

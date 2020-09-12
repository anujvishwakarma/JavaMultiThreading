package com.cadmus.multithreading.DependencyConsumerProblem.PushPullWorkers;

import com.cadmus.multithreading.DependencyConsumerProblem.MyQueue.MyQueue;

public class PushWorker implements Runnable{

    private MyQueue<String> myQueue;

    private String data;

    public PushWorker(MyQueue<String> myQueue, String data) {
        this.myQueue = myQueue;
        this.data = data;
    }

    @Override
    public void run() {
        synchronized (myQueue){
            myQueue.enqueue(data);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Data pushed by PushWorker "+ data +" "+Thread.currentThread().getName());
            myQueue.notifyAll();
        }
    }
}

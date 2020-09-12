package com.cadmus.multithreading.DependencyConsumerProblem.PushPullWorkers;

import com.cadmus.multithreading.DependencyConsumerProblem.MyQueue.MyQueue;

public class PullWorker implements Runnable{

    private MyQueue<String> myQueue;

    public PullWorker(MyQueue<String> myQueue) {
        this.myQueue = myQueue;
    }

    @Override
    public void run() {
        while (true){
            synchronized (myQueue){
                while (myQueue.isEmpty()){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String data = myQueue.dequeue();
                System.out.println("Data consumed by PullWorker-->Consumer "+ Thread.currentThread().getName()+ ": "+ data);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myQueue.notifyAll();
            }
        }
    }
}

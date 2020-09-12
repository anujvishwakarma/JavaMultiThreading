package com.cadmus.multithreading.DependencyConsumerProblemWithMap.PushPullWorkers;

import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.Message;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.MyQueue;

public class PushWorker implements Runnable{

    private MyQueue<Message> myQueue;

    private Message message;

    public PushWorker(MyQueue<Message> myQueue, Message message) {
        this.myQueue = myQueue;
        this.message = message;
    }

    @Override
    public void run() {
        synchronized (myQueue){
            myQueue.enqueue(message);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Data pushed by PushWorker "+ message.getData() +" "+Thread.currentThread().getName());
            myQueue.notifyAll();
        }
    }
}

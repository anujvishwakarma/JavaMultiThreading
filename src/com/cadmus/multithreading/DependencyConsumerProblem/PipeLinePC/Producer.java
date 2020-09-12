package com.cadmus.multithreading.DependencyConsumerProblem.PipeLinePC;

import com.cadmus.multithreading.DependencyConsumerProblem.MyQueue.MyQueue;
import org.json.JSONObject;

import java.util.UUID;

public class Producer implements Runnable{

    private MyQueue<String> myQueue;

    private int maxData;

    public Producer(MyQueue<String> myQueue, int maxData) {
        this.myQueue = myQueue;
        this.maxData = maxData;
    }

    @Override
    public void run() {
        while (true){
            synchronized (myQueue){
                while (myQueue.size() == maxData){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //generate random jsonString
                String data = getRandomJsonData();
                System.out.println("Data produced by pipeLineProducer : "+data);
                myQueue.enqueue(data);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myQueue.notifyAll();
            }
        }
    }

    synchronized private String getRandomJsonData(){
        JSONObject currentObject = new JSONObject();
        currentObject.put("Name", UUID.randomUUID().toString());
        currentObject.put("Data",UUID.randomUUID().toString());
        return currentObject.toString();
    }
}

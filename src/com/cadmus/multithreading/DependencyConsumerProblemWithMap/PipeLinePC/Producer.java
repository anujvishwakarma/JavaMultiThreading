package com.cadmus.multithreading.DependencyConsumerProblemWithMap.PipeLinePC;

import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.Message;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.MyQueue;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Producer implements Runnable{

    private MyQueue<Message> myQueue;

    private int maxData;

    ConcurrentHashMap<String, List<String>> messageStatusMap;

    public Producer(MyQueue<Message> myQueue, int maxData, ConcurrentHashMap<String, List<String>> messageStatusMap) {
        this.myQueue = myQueue;
        this.maxData = maxData;
        this.messageStatusMap = messageStatusMap;
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
                Message message = getRandomJsonData();
                System.out.println("Data produced by pipeLineProducer : "+message.getData());
                myQueue.enqueue(message);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myQueue.notifyAll();
            }
        }
    }

    synchronized private Message getRandomJsonData(){

        JSONObject currentObject = new JSONObject();
        currentObject.put("Name", UUID.randomUUID().toString());
        currentObject.put("Data",UUID.randomUUID().toString());
        String id = UUID.randomUUID().toString();

        //create message with Id, currentJSON object String.
        List<String> interactedConsumersList = new ArrayList<>();
        messageStatusMap.put(id, interactedConsumersList);
        return new Message(id, currentObject.toString(), interactedConsumersList);
    }
}

package com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.NoSuchElementException;

public class MyQueue<Item> {

    private static final int capacity = 100;

    private Item[] queueElements;
    private String queueName;
    private int n;
    private int first;
    private int last;

    public MyQueue(String queueName){
        queueElements = (Item[]) new Object[capacity];
        n = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public int size(){
        return n;
    }

    private void resize(int newCapacity){
        assert newCapacity >= n;
        Item[] copyData = (Item[]) new Object[newCapacity];
        for(int i=0;i<n;i++){
            copyData[i] = queueElements[(first+i)%queueElements.length];
        }
        queueElements = copyData;
        first = 0;
        last = n;
    }


    public void enqueue(Item item){
        if(!isValidMessage(item)){
            System.out.println("Not valid data for Queue "+ queueName);
            return;
        }
        if(n == queueElements.length) {
            resize(2 * queueElements.length);
        }
        queueElements[last++] = item;
        last = last % queueElements.length;
        n++;
    }

    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException("Queue underFlow");
        }
        Item item = queueElements[first];
        queueElements[first++] = null;
        n--;
        first = first %queueElements.length;
        return item;
    }


    public boolean isValidMessage(Object message){
        String data = ((Message)message).getData();
        try{
            new JSONObject(data);
        }catch (JSONException ex1){
            try{
                new JSONArray(data);
            }catch (JSONException ex2){
                return false;
            }
        }
        return true;
    }
}

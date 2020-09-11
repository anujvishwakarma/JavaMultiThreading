package com.cadmus.multithreading.MultipleConsumerProducer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainRunner {

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> myQueue = new LinkedList<>();
        ArrayList<Thread> producers = new ArrayList<>();
        ArrayList<Thread> consumers = new ArrayList<>();
        for(int i=0;i<5;i++){
            producers.add(new Thread(new Producer(myQueue, 10), "ProducerThread " + i));
            consumers.add(new Thread(new Consumer(myQueue, 10), "ConsumerThread " + i));
        }
        for (int i=0;i<5;i++){
            producers.get(i).start();
            consumers.get(i).start();
        }
    }
}

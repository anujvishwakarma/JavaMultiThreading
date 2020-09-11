package com.cadmus.multithreading.ProducerConsumer;

import java.util.LinkedList;
import java.util.Queue;

public class MainRunner {

    public static void main(String[] args) throws InterruptedException {

        Queue<Integer> myQueue = new LinkedList<>();

        Thread producer = new Thread(new Producer(myQueue, 10), "ProducerThread");
        Thread consumer = new Thread(new Consumer(myQueue, 10), "ConsumerThread");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();;

    }

}

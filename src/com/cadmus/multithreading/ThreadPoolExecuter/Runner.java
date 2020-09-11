package com.cadmus.multithreading.ThreadPoolExecuter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Runner {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++){
            Runnable current_worker = new Thread(new WorkerThread(), "Thread "+i);
            executorService.submit(current_worker);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){ }
        System.out.println("Runnable task completed");
    }
}

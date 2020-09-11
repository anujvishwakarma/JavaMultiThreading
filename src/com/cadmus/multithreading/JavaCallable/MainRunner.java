package com.cadmus.multithreading.JavaCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MainRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Future<String>> futures =  new ArrayList<>();
        Callable<String> callableWorker = new CallableWorker();
        for(int i=0;i<10;i++){
            Future<String> currentFuture = executorService.submit(callableWorker);
            futures.add(currentFuture);
        }
        for(Future<String> future: futures){
            System.out.println(future.get());
        }
    }
}

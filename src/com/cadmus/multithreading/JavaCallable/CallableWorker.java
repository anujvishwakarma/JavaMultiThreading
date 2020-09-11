package com.cadmus.multithreading.JavaCallable;

import java.util.concurrent.Callable;

public class CallableWorker implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(2000);
        System.out.println("Current Thread is "+ Thread.currentThread().getName());
        return "Done with "+Thread.currentThread().getName();
    }
}

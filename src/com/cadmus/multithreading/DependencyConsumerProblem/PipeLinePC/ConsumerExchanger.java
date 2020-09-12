package com.cadmus.multithreading.DependencyConsumerProblem.PipeLinePC;

import com.cadmus.multithreading.DependencyConsumerProblem.MyQueue.MyQueue;
import com.cadmus.multithreading.DependencyConsumerProblem.PushPullWorkers.PushWorker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerExchanger implements Runnable{

    private MyQueue<String> myQueue;
    private List<MyQueue<String>> registeredConsumersQueues;
    boolean hasNewData = false;
    private ExecutorService executorService;

    public ConsumerExchanger(MyQueue<String> myQueue, List<MyQueue<String>> registeredConsumersQueues) {
        this.myQueue = myQueue;
        this.registeredConsumersQueues = registeredConsumersQueues;
        executorService = Executors.newFixedThreadPool(registeredConsumersQueues.size());
    }

    @Override
    public void run() {
        while (true){
            String data = "";
            synchronized (myQueue){
                while (myQueue.isEmpty()){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                data = (String) myQueue.dequeue();
                System.out.println("Data TakenUp by ConsumerExchanger "+ data);
                hasNewData = true;
                myQueue.notifyAll();
            }
            //push to next pipeline only if there is data consumed by above block code.
            if(StringUtils.isNotEmpty(data)){
                populateRegisteredQueues(data);
            }
        }
    }

    public void populateRegisteredQueues(String data){
        for(MyQueue<String> currentQueue : registeredConsumersQueues){
            //https://stackoverflow.com/questions/43134998/is-it-reasonable-to-synchronize-on-a-local-variable
            Runnable current_worker = new Thread(new PushWorker(currentQueue, data));
            executorService.submit(current_worker);
        }
        //executorService.shutdown();
    }
}

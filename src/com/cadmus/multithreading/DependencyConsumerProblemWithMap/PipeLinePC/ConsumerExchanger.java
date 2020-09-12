package com.cadmus.multithreading.DependencyConsumerProblemWithMap.PipeLinePC;

import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.Message;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.MyQueue;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.PushPullWorkers.PushWorker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConsumerExchanger implements Runnable{

    private MyQueue<Message> myQueue;
    private List<MyQueue<Message>> registeredConsumersQueues;
    boolean hasNewData = false;
    private ExecutorService executorService;

    public ConsumerExchanger(MyQueue<Message> myQueue, List<MyQueue<Message>> registeredConsumersQueues) {
        this.myQueue = myQueue;
        this.registeredConsumersQueues = registeredConsumersQueues;
        executorService = Executors.newFixedThreadPool(registeredConsumersQueues.size());
    }

    @Override
    public void run() {
        while (true){
            String data = "";
            Message message;
            synchronized (myQueue){
                while (myQueue.isEmpty()){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                message = myQueue.dequeue();
                data = message.getData();
                System.out.println("Data TakenUp by ConsumerExchanger "+ data);
                hasNewData = true;
                myQueue.notifyAll();
            }
            //push to next pipeline only if there is data consumed by above block code.
            if(StringUtils.isNotEmpty(data)){
                populateRegisteredQueues(message);
            }
        }
    }

    public void populateRegisteredQueues(Message message){
        for(MyQueue<Message> currentQueue : registeredConsumersQueues){
            //https://stackoverflow.com/questions/43134998/is-it-reasonable-to-synchronize-on-a-local-variable
            Runnable current_worker = new Thread(new PushWorker(currentQueue, message));
            executorService.submit(current_worker);
        }
        //executorService.shutdown();
    }
}

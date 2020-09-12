package com.cadmus.multithreading.DependencyConsumerProblemWithMap.PushPullWorkers;

import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.Message;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.MyQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PullWorker implements Runnable{

    private final MyQueue<Message> myQueue;

    private List<String> dependencies = new ArrayList<>();

    ConcurrentHashMap<String, List<String>> messageStatusMap;

    private final int taskTime;

    private ExecutorService executorService;

    public PullWorker(MyQueue<Message> myQueue, ConcurrentHashMap<String, List<String>> messageStatusMap, int taskTime) {
        this.myQueue = myQueue;
        this.messageStatusMap = messageStatusMap;
        this.taskTime = taskTime;
    }

    public void setDependencies( List<String> dependentThreadId){
        this.dependencies = dependentThreadId;

        //need executor Service only if there are dependencies;
        executorService = Executors.newFixedThreadPool(1);
    }

    @Override
    public void run() {
        while (true){
            boolean rePush = false;
            Message rePushMessage = null;
            synchronized (myQueue){
                while (myQueue.isEmpty()){
                    try {
                        myQueue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = myQueue.dequeue();
                if(dependencies.size() > 0){
                    boolean canConsume = canConsumeMessage(message);
                    if(canConsume){
                        System.out.println("Message already consumed by dependent Threads "+ "Now Consuming by PullWorker --> Consumer "+
                                Thread.currentThread().getName()+ ": "+ message.getData());

                    }else{
                        System.out.println("Cannot consume : waiting for dependentThreads to consume message" + Thread.currentThread().getName()+ ": "+ message.getData());
                        //since message is not consumed we need to push it again to myQueue, for this create an independentThread
                        rePush = true;
                        rePushMessage= message;
                    }
                }else{
                    System.out.println("Data consumed by PullWorker --> Consumer "+ Thread.currentThread().getName()+ ": "+ message.getData());
                }
                //if not rePushed it means it got consumed so updating the
                if(!rePush){
                    messageStatusMap.get(message.getId()).add(Thread.currentThread().getName());
                }
                try {
                    Thread.sleep(taskTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                myQueue.notifyAll();
            }
            if(rePush && rePushMessage!=null){
                rePushUnConsumedMessage(myQueue, rePushMessage);
                rePush = true;
            }
        }
    }

    /**
     * Check if the current Message is already consumed by the other depdendent Threads.
     * @param message
     * @return
     */
    synchronized private boolean canConsumeMessage(Message message){
        List<String > threadsConsumedCurrentMessages = messageStatusMap.get(message.getId());
        for(String currentDependentThread : dependencies){
            if(!threadsConsumedCurrentMessages.contains(currentDependentThread)){
                return false;
            }
        }
        return true;
    }

    synchronized private void rePushUnConsumedMessage(MyQueue<Message> myQueue, Message message){
        Runnable current_worker = new Thread(new PushWorker( myQueue, message));
        executorService.submit(current_worker);
    }
}

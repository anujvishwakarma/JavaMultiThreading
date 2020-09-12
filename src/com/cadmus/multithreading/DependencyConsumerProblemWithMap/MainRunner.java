package com.cadmus.multithreading.DependencyConsumerProblemWithMap;

import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.Message;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue.MyQueue;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.PipeLinePC.ConsumerExchanger;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.PipeLinePC.Producer;
import com.cadmus.multithreading.DependencyConsumerProblemWithMap.PushPullWorkers.PullWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MainRunner {

    public static void main(String[] args) {

        //**********************************************************************************************************************//
        ConcurrentHashMap<String, List<String> > messageStatusMap = new ConcurrentHashMap<>();

        //**********************************************************************************************************************//

        MyQueue<Message> mainPipelineData = new MyQueue<>("PipeLineQueue");

        //create pipeLineProducer and start the threads.
        Producer pipeLineProducer = new Producer(mainPipelineData, 10, messageStatusMap);
        Thread pipeLineProducerThread = new Thread(pipeLineProducer, "pipeLineProducerThread");
        pipeLineProducerThread.start();


        //**********************************************************************************************************************//

        //Create Pull workers thread/Queues.
        MyQueue<Message> myQueueThreadA = new MyQueue<>("PushWorkerThreadA");
        MyQueue<Message> myQueueThreadB = new MyQueue<>("PushWorkerThreadB");
        MyQueue<Message> myQueueThreadC = new MyQueue<>("PushWorkerThreadC");
        MyQueue<Message> myQueueThreadD = new MyQueue<>("PushWorkerThreadD");


        List<MyQueue<Message>> internalPullPushQueues = new ArrayList<>();
        internalPullPushQueues.add(myQueueThreadA);
        internalPullPushQueues.add(myQueueThreadB);
        internalPullPushQueues.add(myQueueThreadC);
        internalPullPushQueues.add(myQueueThreadD);

        //**********************************************************************************************************************//

        //create PullWorkers
        PullWorker pullWorkerA = new PullWorker(myQueueThreadA, messageStatusMap, 2000);
        PullWorker pullWorkerB = new PullWorker(myQueueThreadB, messageStatusMap, 3000);
        PullWorker pullWorkerC = new PullWorker(myQueueThreadC, messageStatusMap, 6000);
        PullWorker pullWorkerD = new PullWorker(myQueueThreadD, messageStatusMap, 2000);

        /*################################################
         Consumer PullWorkerThreadA is dependent on (PullWorkerThreadB + PullWorkerThreadC)
         Consumer PullWorkerThreadD has no interdependencies.

        For each message M1, PullWorkerThreadA will wait to get first consumed by PullWorkerThreadB and PullWorkerThreadC

        //###############################################*/

        List<String> pullWorkerADependencies = new ArrayList<>();
        pullWorkerADependencies.add("PullWorkerThreadB");
        pullWorkerADependencies.add("PullWorkerThreadC");
        pullWorkerA.setDependencies(pullWorkerADependencies);

        //###############################################*/

        Thread pullWorkerThreadA = new Thread(pullWorkerA, "PullWorkerThreadA");
        Thread pullWorkerThreadB = new Thread(pullWorkerB, "PullWorkerThreadB");
        Thread pullWorkerThreadC = new Thread(pullWorkerC, "PullWorkerThreadC");
        Thread pullWorkerThreadD = new Thread(pullWorkerD, "PullWorkerThreadD");


        pullWorkerThreadA.start();
        pullWorkerThreadB.start();
        pullWorkerThreadC.start();
        pullWorkerThreadD.start();

        //**********************************************************************************************************************//

        ConsumerExchanger consumerExchanger = new ConsumerExchanger(mainPipelineData,internalPullPushQueues);
        Thread consumerExchangerThread = new Thread(consumerExchanger, "ConsumerExchangerThread");
        consumerExchangerThread.start();

        //**********************************************************************************************************************//

    }

}

package com.cadmus.multithreading.DependencyConsumerProblem;

import com.cadmus.multithreading.DependencyConsumerProblem.MyQueue.MyQueue;
import com.cadmus.multithreading.DependencyConsumerProblem.PipeLinePC.ConsumerExchanger;
import com.cadmus.multithreading.DependencyConsumerProblem.PipeLinePC.Producer;
import com.cadmus.multithreading.DependencyConsumerProblem.PushPullWorkers.PullWorker;

import java.util.ArrayList;
import java.util.List;

public class MainRunner {

    public static void main(String[] args) {
        //**********************************************************************************************************************//

        MyQueue<String> mainPipelineData = new MyQueue<>("PipeLineQueue");

        //create pipeLineProducer and start the threads.
        Producer pipeLineProducer = new Producer(mainPipelineData, 10);
        Thread pipeLineProducerThread = new Thread(pipeLineProducer, "pipeLineProducerThread");
        pipeLineProducerThread.start();


        //**********************************************************************************************************************//

        //Create Pull workers thread/Queues.
        MyQueue<String> myQueueThreadA = new MyQueue<>("PushWorkerThreadA");
        MyQueue<String> myQueueThreadB = new MyQueue<>("PushWorkerThreadB");

        List<MyQueue<String>> internalPullPushQueues = new ArrayList<>();
        internalPullPushQueues.add(myQueueThreadA);
        internalPullPushQueues.add(myQueueThreadB);

        //**********************************************************************************************************************//

        //create PullWorkers
        Thread pullWorkerThreadA = new Thread(new PullWorker(myQueueThreadA), "PullWorkerThreadA");
        Thread pullWorkerThreadB = new Thread(new PullWorker(myQueueThreadB), "PullWorkerThreadB");

        pullWorkerThreadA.start();
        pullWorkerThreadB.start();

        //**********************************************************************************************************************//

        ConsumerExchanger consumerExchanger = new ConsumerExchanger(mainPipelineData,internalPullPushQueues);
        Thread consumerExchangerThread = new Thread(consumerExchanger, "ConsumerExchangerThread");
        consumerExchangerThread.start();

        //**********************************************************************************************************************//

    }

}

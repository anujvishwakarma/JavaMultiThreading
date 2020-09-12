package com.cadmus.multithreading.DependencyConsumerProblemWithMap.MyQueue;

import java.util.List;

public class Message {

    private String id;

    private String data;

    private List<String> interactedConsumers;

    public Message(String id, String data, List<String> interactedConsumers) {
        this.id = id;
        this.data = data;
        this.interactedConsumers = interactedConsumers;
    }

    public String getId() {
        return id;
    }

    public String getData() {
        return data;
    }

    public List<String> getInteractedConsumers() {
        return interactedConsumers;
    }
}

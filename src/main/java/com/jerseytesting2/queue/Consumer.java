package com.jerseytesting2.queue;

import com.jerseytesting2.aux.Request;
import com.jerseytesting2.calculator.Client;

import java.util.concurrent.ArrayBlockingQueue;

public class Consumer implements Runnable{


    private ArrayBlockingQueue queue;
    private String uri;
    private String location;


    public Consumer(ArrayBlockingQueue queue, String uri, String location) {
        this.queue = queue;
        this.uri = uri;
        this.location = location;

    }

    public void run() {

        try {
            Queue.requestTreatment((Request) queue.take(), uri, location);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
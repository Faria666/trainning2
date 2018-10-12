package com.client.queue;

import com.types.Request;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class Consumer extends Thread{


    private ArrayBlockingQueue queue;
    private String uri;
    private String location;


    public Consumer(ArrayBlockingQueue queue, String uri, String location) {
        this.queue = queue;
        this.uri = uri;
        this.location = location;

    }

    public void run() {

        while(!queue.isEmpty()) {
            try {
                System.out.println("Consumer "+Thread.currentThread().getName()+" --> START");
                Queue.requestTreatment((Request) queue.take(), uri, location);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }
}
package com.client.queue;

import com.types.Request;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class Consumer extends Thread{


    private ArrayBlockingQueue queue;
    private String uri;
    private String location;
    private String filename;


    public Consumer(ArrayBlockingQueue queue, String uri, String location, String filename) {
        this.queue = queue;
        this.uri = uri;
        this.location = location;
        this.filename = filename;
    }

    public void run() {

        while(!queue.isEmpty()) {
            try {
                System.out.println("Consumer "+Thread.currentThread().getName()+" --> START");
                Queue.requestTreatment((Request) queue.take(), uri, location, filename);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
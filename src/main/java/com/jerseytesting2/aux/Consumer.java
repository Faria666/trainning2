package com.jerseytesting2.aux;

import com.jerseytesting2.rest.RESTClientPost;

import java.util.concurrent.ArrayBlockingQueue;

public class Consumer implements Runnable{


    private ArrayBlockingQueue queue;


    public Consumer(ArrayBlockingQueue queue) {
        this.queue = queue;

    }

    public void run() {

        try {
            RESTClientPost.requestTreatment((Request) queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
package com.jerseytesting.aux;

import com.jerseytesting.rest.RESTClientPost;

import java.sql.SQLException;
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
package com.jerseytesting.aux;

import com.jerseytesting.rest.RESTClientPost;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class Consumer implements Runnable{


    protected ArrayBlockingQueue queue;


    public Consumer(ArrayBlockingQueue queue) {
        this.queue = queue;

    }

    public void run() {

        try {
            System.out.println("HERE1");
            RESTClientPost.requestTreatment((Request) queue.take());
            System.out.println("HERE2");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
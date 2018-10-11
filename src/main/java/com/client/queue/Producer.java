package com.client.queue;

import com.types.Request;

import java.util.concurrent.ArrayBlockingQueue;

public class Producer implements Runnable{

    protected ArrayBlockingQueue queue;
    private Request elem;

    public Producer(ArrayBlockingQueue queue, Request elem) {
        this.queue = queue;
        this.elem = elem;
    }

    public void run() {
        try {
            queue.put(elem);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


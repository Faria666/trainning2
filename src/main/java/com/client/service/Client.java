package com.client.service;

import com.client.typeofobject.Request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.client.others.*;
import com.client.queue.Consumer;
import com.client.queue.Queue;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static com.client.others.Directory.watchDirectory;

public class Client {

    @JsonIgnoreProperties(ignoreUnknown = true)

    private static final String INPUT_DIRECTORY = "src/main/resources/files/input/";
    private static final String OUTPUT_DIRECTORY = "src/main/resources/files/output/";
    private static final String UNSUPPORTED_DIRECTORY = "src/main/resources/files/unsupported/";
    private static final String URI = "http://localhost:8080/calculator";
    private static final String LOCATION = "/calc";


    public static void main(String[] args){

        boolean changes;

        ArrayBlockingQueue queue;

        ArrayList<Request> requestList;

        do {
            changes = watchDirectory(INPUT_DIRECTORY);

            requestList = FileFunctions.processFiles(INPUT_DIRECTORY, OUTPUT_DIRECTORY, UNSUPPORTED_DIRECTORY);

            queue = Queue.createQueue(requestList);

            while(queue.size()!= 0){
                Consumer consumer = new Consumer(queue, URI, LOCATION);
                new Thread(consumer).start();
            }
        } while (changes);
    }
}

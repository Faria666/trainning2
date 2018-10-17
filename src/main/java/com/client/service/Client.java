package com.client.service;

import com.client.others.FileFunctions;
import com.client.queue.Consumer;
import com.client.queue.Queue;
import com.types.Request;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import static com.client.others.Directory.watchDirectory;


public class Client {

    @JsonIgnoreProperties(ignoreUnknown = true)

    private static final String INPUT_DIRECTORY = "src/main/resources/files/input/";
    private static final String OUTPUT_DIRECTORY = "src/main/resources/files/output/";
    private static final String UNSUPPORTED_DIRECTORY = "src/main/resources/files/unsupported/";
    private static final String INVALID_LINES_FILE = "src/main/resources/files/invalid.txt";
    private static final String URI = "http:////172.17.0.2:8080/calculator";//172.17.0.2
    private static final String LOCATION = "/calc";
    private static final int N_THREADS = 2;


    public static void main(String[] args){

        boolean changes;
        ArrayBlockingQueue queue;
        ArrayList<Request> requestList;

        do {
            changes = watchDirectory(INPUT_DIRECTORY);

            requestList = FileFunctions.processFiles(INPUT_DIRECTORY, OUTPUT_DIRECTORY, UNSUPPORTED_DIRECTORY);

            queue = Queue.createQueue(requestList);

            for(int i = 0; i< N_THREADS; i++) {
                Consumer consumer = new Consumer(queue, URI, LOCATION, INVALID_LINES_FILE);
                Thread consumerThread = new Thread(consumer);
                consumerThread.start();
            }

        } while (changes);
    }
}

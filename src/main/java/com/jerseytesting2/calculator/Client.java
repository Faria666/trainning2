package com.jerseytesting2.calculator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerseytesting2.aux.*;
import com.jerseytesting2.queue.Consumer;
import com.jerseytesting2.queue.Queue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

import static com.jerseytesting2.aux.Directory.watchDirectory;

public class Client {

    @JsonIgnoreProperties(ignoreUnknown = true)

    private static final String INPUT_DIRECTORY = "src/main/resources/files/input/";
    private static final String OUTPUT_DIRECTORY = "src/main/resources/files/output/";
    private static final String LOCATION = "/calc";
    private static final String URI = "http://localhost:8080/calculator";



    public static void main(String[] args){

        boolean changes;

        ArrayBlockingQueue queue;

        ArrayList<Request> requestList;

        do {
            changes = watchDirectory(INPUT_DIRECTORY);

            requestList = Files.processFiles(INPUT_DIRECTORY, OUTPUT_DIRECTORY);

            queue = Queue.createQueue(requestList);

            while(queue.size()!= 0){
                Consumer consumer = new Consumer(queue, URI, LOCATION);
                new Thread(consumer).start();
            }
        } while (changes);
    }
}

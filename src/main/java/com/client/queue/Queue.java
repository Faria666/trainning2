package com.client.queue;

import com.client.others.Connection;
import com.client.others.Database;
import com.types.Answer;
import com.types.Request;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Queue {

    /**
     * Creates and populates the queue composed by all the requests read from de CSV files
     * @param requestList is the Requests objects that contains all the info necessary for the server calculate and send back the answer
     * @return returns the queue already populated
     */

    public static ArrayBlockingQueue createQueue(final ArrayList<Request> requestList){

        final ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);

        for (Request aRequestList : requestList) {
            Producer producer = new Producer(queue, aRequestList);
            new Thread(producer).start();
        }
        return queue;
    }

    /**
     * Function that will be running in every thread created
     * @param request is the request to be treated by each thread, that sent it to the server
     */

    static void requestTreatment(final Request request, String uri, String location) throws IOException {
        Answer answer;

        if(request != null)
            try{
                answer = Connection.client(request, uri, location);
                if (answer != null && answer.getOperation().compareTo("none") != 0) {
                    Database.insertJDBC(answer, request);
                }
            }catch (NullPointerException ignored) {
            }

    }


    public Queue() {
    }
}

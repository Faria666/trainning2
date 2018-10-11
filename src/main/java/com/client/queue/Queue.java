package com.client.queue;

import com.client.aux.Answer;
import com.client.aux.Connection;
import com.client.aux.Database;
import com.client.aux.Request;

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

        for(int i = 0; i < requestList.size(); i++){
            Producer producer = new Producer(queue,requestList.get(i));
            new Thread(producer).start();
        }
        return queue;
    }

    /**
     * Function that will be running in every thread created
     * @param request is the request to be treated by each thread, that sent it to the server
     */

    public static void requestTreatment(final Request request, String uri, String location){
        Answer answer = new Answer();

        if(request != null)
            answer = Connection.client(request, uri, location);
        if(answer.getOperation().compareTo("none")!=0 ) {
            Database.insertJDBC(answer, request);
        }
    }


    public Queue() {
    }
}

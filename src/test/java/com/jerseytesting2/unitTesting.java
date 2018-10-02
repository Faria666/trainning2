package com.jerseytesting2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jerseytesting2.aux.Answer;
import com.jerseytesting2.aux.Consumer;
import com.jerseytesting2.aux.Request;
import com.jerseytesting2.rest.RESTClientPost;
import com.jerseytesting2.rest.RESTServer;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import static org.mockito.Mockito.*;


public class unitTesting {

    RESTServer server = new RESTServer();


    @Test
    public void testRestServer() throws IOException {
        String request = "{" + "\"" + "value1" + "\"" + ":" + "\"" + "12" + "\"" + ", " + "\"" + "value2" + "\"" + ":" + "\"" + "67" + "\"" + ", " + "\"" + "operation" + "\"" + ":" + "\"" + "add" + "\"" + "}";
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testConvertToObj() throws IOException {
        boolean flag = false;
        Request request;
        String json = "{" + "\"" + "value1" + "\"" + ":" + "\"" + "12" + "\"" + ", " + "\"" + "value2" + "\"" + ":" + "\"" + "67" + "\"" + ", " + "\"" + "operation" + "\"" + ":" + "\"" + "add" + "\"" + "}";
        request = server.convertToObj(json);
        if((request.getValue1()==12) && (request.getValue2()==67) && (request.getOperation().compareToIgnoreCase("add")==0))
           flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testConvertToJson() throws JsonProcessingException {
        boolean flag = false;
        Answer answer = new Answer("add",67.0,"12/12/2012");
        String json;
        json = server.convertToJson(answer,"something just for the log");
        if(json.contains("add") && json.contains("67.0") && json.contains("12/12/2012"));
            flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testCalculatorAdd(){
        boolean flag = false;
        Request request = new Request(10,5,"add");
        if(server.calculator(request) == 15)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorMult(){
        boolean flag = false;
        Request request = new Request(10,5,"mult");
        if(server.calculator(request) == 50)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorDiv(){
        boolean flag = false;
        Request request = new Request(10,5,"div");
        if(server.calculator(request) == 2)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorAvg(){
        boolean flag = false;
        Request request = new Request(10,5,"avg");
        if(server.calculator(request) == 7.5)
            flag = true;
        Assert.assertTrue(flag);

    }


    @Test
    public void testBuildAnswer() {
        boolean flag = false;
        double result = 15;
        String operation = "add";
        Answer answer;
        answer = RESTServer.buildAnswer(operation, result);
        if((answer.getOperation().compareToIgnoreCase(operation) == 0) && answer.getResult() == 15)
            flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testReadFileWithFramework() throws IOException {
        boolean flag = false;
        String file = "test.csv";
        ArrayList<Request> requestList;
        ArrayList<String> files = new ArrayList<>();
        files.add(file);

        File oldfile =new File("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/watchTest/Done - test.csv");
        File newfile =new File("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/watchTest/test.csv");

        if(oldfile.renameTo(newfile)){
            System.out.println("Rename succesful");
        }else{
            System.out.println("Rename failed");
        }

        RESTClientPost client = mock(RESTClientPost.class);
        requestList = client.readFileWithFramework("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/watchTest/", "/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/watchTest/", files);
        if(requestList.get(0).getValue1() == 21.34 && requestList.get(0).getValue2( )== 45.02 && requestList.get(0).getOperation().compareToIgnoreCase("add")==0)
            flag = true;


        Assert.assertTrue(flag);
    }

    @Test
    public void testSeekFiles(){
        boolean flag = false;
        ArrayList<String> files;
        RESTClientPost client = mock(RESTClientPost.class);
        files = client.seekFiles("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/seekTest/");
        if(files.get(0).compareToIgnoreCase("Done - test.csv")==0)
            flag = true;
        Assert.assertTrue(flag);
    }


    @Test
    public void testClient(){
        Request request = new Request(10,5,"add");
        RESTClientPost client = mock(RESTClientPost.class);

        client.client(request);
    }

    @Test
    public void testCreateQueue() throws InterruptedException {
        boolean flag = false;
        ArrayList<Request> requests = new ArrayList<>();
        Request request = new Request(10, 5, "add");
        Request auxiliar;
        requests.add(request);
        RESTClientPost client = mock(RESTClientPost.class);
        ArrayBlockingQueue blockingQ =client.createQueue(requests);
        auxiliar = (Request) blockingQ.take();
        if(auxiliar.getValue1() == 10 && auxiliar.getValue2() == 5 && auxiliar.getOperation().compareToIgnoreCase("add")==0)
            flag = true;
        Assert.assertTrue(flag);
    }



    /*@Test
    public void testWatchDirectory(){
        boolean flag = false;
        File file = new File("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/wdTest/newfile.csv");
        RESTClientPost client = mock(RESTClientPost.class);
        when(client.watchDirectory("/home/joao-faria/Desktop/jerseytesting2/src/test/java/com/jerseytesting2/wdTest/")).thenReturn(flag = true);
        Assert.assertTrue(flag);
    }*/



















    @Test
    public void testInsertJDBC(){
        Request request = new Request(10,5,"add");
        Answer answer = new Answer("add",15,"12/12/12 12:12:12");
        RESTClientPost client = mock(RESTClientPost.class);
        client.insertJDBC(answer,request);

    }


}
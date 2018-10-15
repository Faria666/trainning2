package com;

import com.calculator.others.BuildAnswer;
import com.calculator.others.Calculate;
import com.calculator.others.Convertions;
import com.calculator.service.Calculator;
import com.client.others.Database;
import com.client.others.Directory;
import com.client.others.FileFunctions;
import com.client.queue.Queue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.types.Answer;
import com.types.Request;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


public class unitTesting {

    @Test
    public void testBuildAnswerFunction(){

        Answer answer;
        String operation;
        double result;

        operation = "add";
        result = 10;

        answer = BuildAnswer.buildAnswer(operation,result);

        Assert.assertEquals(result, answer.getResult(), 0.0);
        Assert.assertEquals(operation, answer.getOperation());

    }

    @Test
    public void testConvertToObj() throws IOException {

        String json = "{" + "\"" + "value1" + "\""+ ":" + 12 + "," + "\""+ "value2"+ "\"" + ":" + 67 + "," + "\""+ "operation"+ "\"" + ":" + "\""+"mult"+ "\"" + "}";
        Request request;

        request = Convertions.convertToObj(json);

        Assert.assertEquals(12, request.getValue1(),0.0);
        Assert.assertEquals(67,request.getValue2(),0.0);
        Assert.assertEquals(0, "mult".compareToIgnoreCase(request.getOperation()));

    }

    @Test
    public void testConvertToJson() throws JsonProcessingException {

        String json = "{" + "\"" + "answerId" + "\""+ ":" + 0 + "," + "\""+ "result"+ "\"" +":" + 10.0 + "," + "\""+"operation"+ "\"" + ":" + "\""+"add"+ "\"" + "," + "\""+"date"+ "\"" + ":" + "\""+"12/12/12"+ "\""+ "}";
        String jsonResult;
        Answer answer = new Answer("add", 10.0, "12/12/12");

        jsonResult = Convertions.convertToJson(answer, json);

        Assert.assertEquals(json,jsonResult);

    }

    @Test
    public void testCalculate(){

        double result1, result2, result3, result4, result5, value1, value2;
        String operation1, operation2, operation3, operation4, operation5;
        Request request1, request2, request3, request4, request5;

        value1 = 4;
        value2 = 2;

        operation1 = "add";
        operation2 = "mult";
        operation3 = "div";
        operation4 = "avg";
        operation5 = "ktgr";

        request1 = new Request(value1, value2, operation1);
        request2 = new Request(value1, value2, operation2);
        request3 = new Request(value1, value2, operation3);
        request4 = new Request(value1, value2, operation4);
        request5 = new Request(value1, value2, operation5);


        result1 = Calculate.calculator(request1);
        result2 = Calculate.calculator(request2);
        result3 = Calculate.calculator(request3);
        result4 = Calculate.calculator(request4);
        result5 = Calculate.calculator(request5);

        Assert.assertEquals(6, result1, 0.0);
        Assert.assertEquals(8, result2, 0.0);
        Assert.assertEquals(2, result3, 0.0);
        Assert.assertEquals(3, result4, 0.0);
        Assert.assertEquals(-1, result5, 0.0);

    }

    @Test
    public void testSendResponse() throws IOException {

        Response response;
        String json = "{" + "\"" + "value1" + "\""+ ":" + 12 + "," + "\""+ "value2"+ "\"" + ":" + 67 + "," + "\""+ "operation"+ "\"" + ":" + "\""+"mult"+ "\"" + "}";

        response = Calculator.sendResponse(json);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void testCreateQueue() throws InterruptedException {

        Request request1, request2;
        ArrayList requestList = new ArrayList();
        ArrayBlockingQueue queue;

        request1 = new Request(4,2,"add");
        request2 = new Request(6,3,"add");

        requestList.add(request1);
        requestList.add(request2);

        queue = Queue.createQueue(requestList);

        Assert.assertEquals(queue.take(), request1);
        Assert.assertEquals(queue.take(), request2);

    }

    @Test
    public void testInvalidLines(){

        Request request1 = new Request(10,10,"awsda");

        boolean created = FileFunctions.invalidLines(request1, "src/test/resources/invalid.txt");

        Assert.assertTrue(created);
    }

    @Test
    public void testSeekFiles(){

        String directory = "src/test/resources";

        ArrayList<String> files;

        files = Directory.seekFiles(directory);

        Assert.assertTrue(!files.isEmpty());

    }

    @Test
    public void testCalculatorServer() throws IOException {

        String json1 = "{" + "\"" + "value1" + "\""+ ":" + 12 + "," + "\""+ "value2"+ "\"" + ":" + 67 + "," + "\""+ "operation"+ "\"" + ":" + "\""+"mult"+ "\"" + "}";
        String json2 = "{" + "\"" + "value1" + "\""+ ":" + 12 + "," + "\""+ "value2"+ "\"" + ":" + 67 + "," + "\""+ "operation"+ "\"" + ":" + "\""+"gsdgsedg"+ "\"" + "}";

        Response server1 = Calculator.sendResponse(json1);
        Response server2 = Calculator.sendResponse(json2);

        Assert.assertEquals(200, server1.getStatus());
        Assert.assertNotEquals(200, server2.getStatus());

    }

    @Test
    public void testAnswerGets(){

        Answer answer = new Answer("add", 4.5, "12/12/12");

        Assert.assertEquals("add", answer.getOperation());
        Assert.assertEquals(4.5, answer.getResult(),0.0);
        Assert.assertEquals("12/12/12", answer.getDate());
        Assert.assertEquals(0, answer.getAnswerId(),0.0);
    }

    @Test
    public void testRequestGets(){

        Request request = new Request(5,3,"add");

        Assert.assertEquals(5, request.getValue1(),0.0);
        Assert.assertEquals(3, request.getValue2(),0.0);
        Assert.assertEquals("add", request.getOperation());
        Assert.assertEquals(0, request.getRequestId(),0.0);
    }

    @Test
    public void testAnswerSets(){

        Answer answer = new Answer();

        answer.setAnswerId(1);
        answer.setDate("12/12/12");
        answer.setOperation("mult");
        answer.setResult(0);

        Assert.assertEquals(1, answer.getAnswerId(),0.0);
        Assert.assertEquals("12/12/12", answer.getDate());
        Assert.assertEquals("mult", answer.getOperation());
        Assert.assertEquals(0, answer.getResult(),0.0);
    }

    @Test
    public void testRequestSets(){

        Request request = new Request();

        request.setRequestId(1);
        request.setValue1(2);
        request.setValue2(10);
        request.setOperation("mult");

        Assert.assertEquals(1, request.getRequestId(),0.0);
        Assert.assertEquals(2, request.getValue1(),0.0);
        Assert.assertEquals(10, request.getValue2(),0.0);
        Assert.assertEquals("mult", request.getOperation());
    }

    @Test
    public void testReadFileWithFramework() throws IOException {

    String directory = "src/test/resources/";
    Request request = new Request(5,5, "add");
    ArrayList<String> files = new ArrayList<>();

    File file1 = new File(directory + "newfile1.csv");
    File file2 = new File(directory + "newfile2.xml");

    BufferedWriter writer = Files.newBufferedWriter(Paths.get(directory + "newfile1.csv"));

    CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

    csvPrinter.printRecord(5.0, 5.0, "add");
    csvPrinter.flush();

    files.add(file1.getName());
    files.add(file2.getName());

    ArrayList<Request> requestList = FileFunctions.readFileWithFramework(directory, directory, directory, files);

    Assert.assertEquals(request.getOperation(),requestList.get(0).getOperation());
    Assert.assertEquals(request.getValue1(),requestList.get(0).getValue1(),0.0);
    Assert.assertEquals(request.getValue2(),requestList.get(0).getValue2(),0.0);
    }

    @Test
    public void testProcessFiles() throws IOException {
        String outputDirectory = "src/test/resources/";
        String inputDirectory = "src/test/resources/processFiles/";
        Request request = new Request(5,5, "add");
        ArrayList<Request> requestList;

        File file1 = new File(inputDirectory + "newfile1.csv");

        BufferedWriter writer = Files.newBufferedWriter(Paths.get(inputDirectory + "newfile1.csv"));

        CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

        csvPrinter.printRecord(5.0, 5.0, "add");
        csvPrinter.flush();

        requestList = FileFunctions.processFiles(inputDirectory,outputDirectory,outputDirectory);

        Assert.assertEquals(request.getOperation(),requestList.get(0).getOperation());
        Assert.assertEquals(request.getValue1(),requestList.get(0).getValue1(),0.0);
        Assert.assertEquals(request.getValue2(),requestList.get(0).getValue2(),0.0);

    }

    @Test
    public void testInsertJDBC(){

        boolean inserted;
        Answer answer= new Answer("add", 10 , "12/12/12");
        Database db = org.mockito.Mockito.mock(Database.class);
        Request request= new Request(5,5,"add");

        when(db.insertJDBC(answer,request)).thenReturn(true);

        inserted = db.insertJDBC(answer,request);

        Assert.assertTrue(inserted);
    }
}
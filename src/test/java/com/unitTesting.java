package com;

import com.calculator.others.BuildAnswer;
import com.calculator.others.Calculate;
import com.calculator.others.Convertions;
import com.calculator.service.Calculator;
import com.client.queue.Queue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.types.Answer;
import com.types.Request;
import org.junit.Assert;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;


public class unitTesting {

    @Test
    public void testBuildAnswerFuction(){

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
    public void tesSendResponse() throws IOException {

        Response response;
        String json = "{" + "\"" + "value1" + "\""+ ":" + 12 + "," + "\""+ "value2"+ "\"" + ":" + 67 + "," + "\""+ "operation"+ "\"" + ":" + "\""+"mult"+ "\"" + "}";

        response = Calculator.sendResponse(json);

        Assert.assertEquals(200, response.getStatus());

    }

    @Test
    public void testCreateQueue(){

        Request request1, request2;
        ArrayList requestList = new ArrayList();
        ArrayBlockingQueue queue;

        request1 = new Request(4,2,"add");
        request2 = new Request(6,3,"add");

        requestList.add(request1);
        requestList.add(request2);

        queue = Queue.createQueue(requestList);

        Assert.assertTrue(queue.contains(request1));
        Assert.assertTrue(queue.contains(request2));

    }

}
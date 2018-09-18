package com.jerseytesting.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerseytesting.aux.Answer;
import com.jerseytesting.aux.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/calculator")
public class RESTServer {

    private final Logger log = LoggerFactory.getLogger(RESTServer.class);

    private ObjectMapper mapper = new ObjectMapper();

    private Request convertToJson(final String json) throws IOException {

        final Request request;
        log.debug("Processing request: {}", json);

        request = mapper.readValue(json, Request.class);

        return request;
    }

    private String convertToString(final Answer answerObject, final String json) throws JsonProcessingException {
        String answer;

        answer = mapper.writeValueAsString(answerObject);

        log.debug("Retreiving answer {} for request: {}", answer, json);

        return answer;

    }

    private double calculator(final Request request){

        /*double result = 0;
        final String operation;
        final double value1;
        final double value2;
        final double sum;
        final double multiplication;
        final double division;
        final double average;

        value1 = request.getValue1();
        value2 = request.getValue2();
        operation = request.getOperation();*/

        log.debug("Processing number: {} and number: {}  with operation: {}", request.getValue1(), request.getValue2(), request.getOperation());

        /*sum = value1 + value2;
        multiplication = value1 * value2;
        division = (value1 / value2);
        average = (sum / 2);

        if (operation.compareToIgnoreCase("add") == 0)
            result = sum;
        else if (operation.compareToIgnoreCase("mult") == 0)
            result = multiplication;
        else if (operation.compareToIgnoreCase("div") == 0)
            result = division;
        else if (operation.compareToIgnoreCase("avg") == 0)
            result = average;*/


        /*BiFunction<Double, Double, Double> calculate = (Value1, Value2) -> {
            if (operation.compareToIgnoreCase("add") == 0) return value1 + value2;
            else if (operation.compareToIgnoreCase("mult") == 0) return value1 * value2;
            else if (operation.compareToIgnoreCase("div") == 0) return value1 / value2;
            else if (operation.compareToIgnoreCase("avg") == 0) return (value1 + value2)/2;
            else return 0.0;
        };

        result = calculate.apply(value1, value2);*/


        /*(request.getValue1(), request.getValue2(), request.getOperation()) -> {
            if (request.getOperation().compareToIgnoreCase("add") == 0) return request.getValue1() + request.getValue2();
            else if (request.getOperation().compareToIgnoreCase("mult") == 0) return request.getValue1() * request.getValue2();
            else if (request.getOperation().compareToIgnoreCase("div") == 0) return request.getValue1() / request.getValue2();
            else if (request.getOperation().compareToIgnoreCase("avg") == 0) return (request.getValue1() + request.getValue2())/2;
        };*/



        Calculate calculate = (value1, value2, operation) -> {

            if (operation.compareToIgnoreCase("add") == 0) return value1 + value2;
            else if (operation.compareToIgnoreCase("mult") == 0) return value1 * value2;
            else if (operation.compareToIgnoreCase("div") == 0) return value1 / value2;
            else if (operation.compareToIgnoreCase("avg") == 0) return (value1 + value2)/2;
            else return 0;
        };

        return calculate.obtainResult(request.getValue1(),request.getValue2(),request.getOperation());

    }

    private interface Calculate{
        double obtainResult(double value1, double value2, String operation);
    }

    private Answer buildAnswer(final String operation, final double result){

        String date;
        Answer answerObject;

        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        answerObject = new Answer(operation, result, date);

        return answerObject;

    }

    @POST
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRequest(final String json) throws IOException {

        final double result;
        final String answer;
        final Request request;
        final Answer answerObject;

        request = convertToJson(json);

        result = calculator(request);

        answerObject = buildAnswer(request.getOperation(),result);

        answer = convertToString(answerObject, json);

        return Response.status(Response.Status.OK).entity(answer).build();

    }

}
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

        double result = 0;
        final double value1;
        final double value2;
        final double sum;
        final double multiplication;
        final double division;
        final double average;
        final String operation;


        value1 = request.getA();
        value2 = request.getB();
        operation = request.getOp();

        log.debug("Processing number: {} and number: {}  with operation: {}", value1, value2, operation);

        sum = value1 + value2;
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
            result = average;


        return result;
    }

    private Answer buildAnswer(final String operation, final double result){

        String date;
        String answer;
        Answer answerObject;

        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        answerObject = new Answer(operation, result, date);

        return answerObject;


    }

    @POST
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRequest(final String json) throws IOException {

        double result;
        String answer;
        Request request;
        Answer answerObject;

        request = convertToJson(json);

        result = calculator(request);

        answerObject = buildAnswer(request.getOp(),result);

        answer = convertToString(answerObject, json);

        return Response.status(Response.Status.OK).entity(answer).build();

    }

}
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

    /**
     * Function that converts a String parameter into a Request object
     * @param json is the String of the request received from the client
     * @return return an Request object with all the fields containning data from the json String
     * @throws IOException
     */

    private Request convertToObj(final String json) throws IOException {

        final Request request;
        log.debug("Processing request: {}", json);

        request = mapper.readValue(json, Request.class);

        return request;
    }

    /**
     * Function that converts a Answer object into a JSON String where all the data is written in conformity with JSON conventions
     * @param answerObject is the object with all the data about the answer to retrieve to the client
     * @param json is the String that contains the request, only for logging purposes
     * @return return a JSON String with all the data contained in the object
     * @throws JsonProcessingException
     */

    private String convertToString(final Answer answerObject, final String json) throws JsonProcessingException {
        String answer;

        answer = mapper.writeValueAsString(answerObject);

        log.debug("Retrieving answer {} for request: {}", answer, json);

        return answer;
    }

    /**
     * Function that calculates the result of the operation specified in the request from the client
     * @param request is the Request object that contains all the data from the client request
     * @return return the value resultant from the operation
     */

    private double calculator(final Request request){

        log.debug("Processing number: {} and number: {}  with operation: {}", request.getValue1(), request.getValue2(), request.getOperation());

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

    /**
     *Function that builds the answer to send to the client, merge the result of the calculation of the operation, the date and hour of the operation and the operation made
     * @param operation is the operation made
     * @param result is the result of the operation
     * @return returns a Answer object that contains all the data referent to the treeatment of the request from the client
     */

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

        System.out.println("HERE");

        request = convertToObj(json);

        result = calculator(request);

        answerObject = buildAnswer(request.getOperation(),result);

        answer = convertToString(answerObject, json);

        return Response.status(Response.Status.OK).entity(answer).build();
    }
}
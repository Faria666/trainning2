package com.calculator.service;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.calculator.others.Calculate;
import com.calculator.others.BuildAnswer;
import com.types.Answer;
import com.types.Request;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

@Path("/calculator")
public class Calculator {


    @POST
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public static Response sendResponse(final String json) throws IOException {

        final double result;
        final String answer;
        final Request request;
        final Answer answerObject;

        request = Convertions.convertToObj(json);

        result = Calculate.calculator(request);

        if(result != -1) {

            answerObject = BuildAnswer.buildAnswer(request.getOperation(), result);

            answer = Convertions.convertToJson(answerObject, json);

            return Response.status(Response.Status.OK).entity(answer).build();
        }
        else
            return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
package com.calculator.service;

import java.io.IOException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.calculator.calculate.Calculate;
import com.calculator.dataTreatment.BuildAnswer;
import com.calculator.dataTreatment.Convertions;
import com.client.aux.Answer;
import com.client.aux.Request;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

@Path("/calculator")
public class Calculator {


    @POST
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendResponse(final String json) throws IOException {

        final double result;
        final String answer;
        final Request request;
        final Answer answerObject;

        request = Convertions.convertToObj(json);

        result = Calculate.calculator(request);

        answerObject = BuildAnswer.buildAnswer(request.getOperation(),result);

        answer = Convertions.convertToJson(answerObject, json);

        return Response.status(Response.Status.OK).entity(answer).build();
    }
}
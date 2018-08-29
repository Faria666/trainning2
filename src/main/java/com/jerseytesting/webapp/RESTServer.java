package com.jerseytesting.webapp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/calculator")
public class RESTServer {

    private final Logger log = LoggerFactory.getLogger(RESTServer.class);

    private ObjectMapper mapper = new ObjectMapper();

    public Response calculator(final String json){

        double val1;
        double val2;
        double sum;
        double mult;
        double div;
        double avg;
        double res;
        String answer = null;
        String op=null;
        String date=null;
        Answer ans=null;
        Request req=null;

        log.debug("Processing request: {}", json);

        try {
            req = mapper.readValue(json, Request.class);
        } catch (IOException | NullPointerException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("error").build();
        }


        val1 = req.getA();
        val2 = req.getB();
        op = req.getOp();

        log.debug("Processing number: {} and number: {}  with operation: {}", val1, val2, op);

        sum = val1 + val2;
        mult = val1 * val2;
        div = (val1 / val2);
        avg = (sum / 2);

        if (op.compareToIgnoreCase("add") == 0)
            res = sum;
        else if (op.compareToIgnoreCase("mult") == 0)
            res = mult;
        else if (op.compareToIgnoreCase("div") == 0)
            res = div;
        else if (op.compareToIgnoreCase("avg") == 0)
            res = avg;
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("error").build();
        }

        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());


        ans = new Answer(op, res, date);

        try {
            answer = mapper.writeValueAsString(ans);
        } catch (JsonProcessingException e) {
            return Response.status(Response.Status.CONFLICT).entity("error").build();
        }

        log.debug("Retreiving answer {} for request: {}", answer, json);

        return Response.status(Response.Status.OK).entity(answer).build();

    }

     /**
     * this is the REST server that will answer the REST requests made from the REST clients, those requests only be successfully answered if the request is in JSON String format
     * @param json is the JSON String request that will be converted to a Request Object
     * @return returns the answer for the request made by the REST client in JSON String format
     */

    @POST
    @Path("/calc")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRequest(final String json) {

        return calculator(json);

    }

}
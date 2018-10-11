package com.client.aux;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.client.service.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Connection {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    /**
     * Connects to the REST server and send a request waiting for the response
     * @param requestObject is the request sent to the server to calculate
     * @return returns the answer in the form of a Answer object
     */

    public static Answer client(final Request requestObject, String uri, String location){

        final ObjectMapper mapper = new ObjectMapper();
        String request = null;
        final String answer;

        Answer answerObject = new Answer();

        try {
            request = mapper.writeValueAsString(requestObject);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        javax.ws.rs.client.Client client = ClientBuilder.newClient();

        WebTarget target = client.target(uri);

        WebTarget answerWebTarget;

        answerWebTarget = target.path(location);

        Invocation.Builder invocationBuilder;

        invocationBuilder = answerWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(request,MediaType.APPLICATION_JSON));

        System.out.print("\nStatus: ");

        System.out.println(response.getStatus());

        answer = response.readEntity(String.class);

        System.out.println(answer + "\n");

        try {
            answerObject = mapper.readValue(answer, Answer.class);
        } catch (IOException e) {
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            answerObject.setOperation("none");
            answerObject.setResult(0);
            answerObject.setDate(date);
        }
        log.debug("Request processed, answer: {} {} {}", answerObject.getOperation(), answerObject.getResult(), answerObject.getDate());
        return answerObject;
    }

    public Connection() {
    }
}

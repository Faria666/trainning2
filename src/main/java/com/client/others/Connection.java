package com.client.others;

import com.client.service.Client;
import com.types.Answer;
import com.types.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class Connection {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    /**
     * Connects to the REST server and send a request waiting for the response
     * @param requestObject is the request sent to the server to calculate
     * @return returns the answer in the form of a Answer object
     */

    public static Answer client(final Request requestObject, String uri, String location, String filename) {

        final ObjectMapper mapper = new ObjectMapper();
        String request = null;
        final String answer;

        Answer answerObject = null;

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

        answer = response.readEntity(String.class);

        System.out.println(answer + "\n");

        if(response.getStatus() != 400) {

            try {
                answerObject = mapper.readValue(answer, Answer.class);
            } catch (IOException e) {
                FileFunctions.invalidLines(requestObject, filename);
            }
            if (answerObject != null) {
                log.debug("Request processed, answer: {} {} {}", answerObject.getOperation(), answerObject.getResult(), answerObject.getDate());
            }
            return answerObject;

        }
        else{
            FileFunctions.invalidLines(requestObject, filename);
        }
        return null;

    }

    public Connection() {
    }
}

package com.calculator.dataTreatment;

import com.calculator.service.Calculator;
import com.client.aux.Answer;
import com.client.aux.Request;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Convertions {

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Function that converts a String parameter into a Request object
     * @param json is the String of the request received from the client
     * @return return an Request object with all the fields containning data from the json String
     * @throws IOException
     */

    public static Request convertToObj(final String json) throws IOException {

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

    public static String convertToJson(final Answer answerObject, final String json) throws JsonProcessingException {
        String answer;

        answer = mapper.writeValueAsString(answerObject);

        log.debug("Retrieving answer {} for request: {}", answer, json);

        return answer;
    }


    public Convertions() {
    }
}

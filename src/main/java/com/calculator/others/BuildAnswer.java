package com.calculator.others;

import com.types.Answer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BuildAnswer {

    /**
     *Function that builds the answer to send to the client, merge the result of the calculation of the operation, the date and hour of the operation and the operation made
     * @param operation is the operation made
     * @param result is the result of the operation
     * @return returns a Answer object that contains all the data referent to the treatment of the request from the client
     */

    public static Answer buildAnswer(final String operation, final double result){

        String date;
        Answer answerObject;

        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        answerObject = new Answer(operation, result, date);

        return answerObject;
    }

}

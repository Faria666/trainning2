package com.calculator.calculate;

import com.calculator.service.Calculator;
import com.client.aux.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculate {

    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    /**
     * Function that calculates the result of the operation specified in the request from the client
     * @param request is the Request object that contains all the data from the client request
     * @return return the value resultant from the operation made
     */

    public static double calculator(final Request request){

        log.debug("Processing number: {} and number: {}  with operation: {}", request.getValue1(), request.getValue2(), request.getOperation());

        Calculation calculate = (value1, value2, operation) -> {

            if (operation.compareToIgnoreCase("add") == 0) return value1 + value2;
            else if (operation.compareToIgnoreCase("mult") == 0) return value1 * value2;
            else if (operation.compareToIgnoreCase("div") == 0) return value1 / value2;
            else if (operation.compareToIgnoreCase("avg") == 0) return (value1 + value2)/2;
            else return 0;
        };

        return calculate.obtainResult(request.getValue1(),request.getValue2(),request.getOperation());
    }

    public interface Calculation{
        double obtainResult(double value1, double value2, String operation);
    }


    public Calculate() {
    }
}

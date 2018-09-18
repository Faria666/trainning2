package com.jerseytesting.aux;

public class Request {
    private double value1;
    private double value2;
    private String operation;

    /**
     * his is used to get the first number in the request object
     * @return return the value of the number
     */

    public double getValue1() { return value1; }

    /**
     * this is used to set the value of the second number in the request object
     * @param value1 is the first number that will be introduced in the request object field
     */

    public void setValue1(double value1) {
        this.value1 = value1;
    }

    /**
     * his is used to get the second number in the request object
     * @return return the value of the number
     */

    public double getValue2() {
        return value2;
    }

    /**
     * this is used to set the value of the second number in the request object
     * @param value2 is the second number that will be introduced in the request object field
     */

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    /**
     * this is used to get the operation in the request object
     * @return return the value of the operation
     */

    public String getOperation() {
        return operation;
    }

    /**
     *  this is used to set the operation in the request object
     * @param operation is the operation that will be introduced in the request object field
     */

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * This constructs the request made to the REST server
     * @param value1 is the first number that will be used to make the operation
     * @param value2 is the second number that will be used to make the operation
     * @param operation is the math operation to do using the two numbers referred before
     */

    public Request(double value1, double value2, String operation) {
        this.value1 = value1;
        this.value2 = value2;
        this.operation = operation;
    }

    /**
     * Necessary to the json parser
     */

    public Request() {
    }

}
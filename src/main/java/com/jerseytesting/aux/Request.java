package com.jerseytesting.aux;

public class Request {
    private double a;
    private double b;
    private String op;

    /**
     * his is used to get the first number in the request object
     * @return return the value of the number
     */

    public double getA() { return a; }

    /**
     * this is used to set the value of the second number in the request object
     * @param a is the first number that will be introduced in the request object field
     */

    public void setA(double a) {
        this.a = a;
    }

    /**
     * his is used to get the second number in the request object
     * @return return the value of the number
     */

    public double getB() {
        return b;
    }

    /**
     * this is used to set the value of the second number in the request object
     * @param b is the second number that will be introduced in the request object field
     */

    public void setB(double b) {
        this.b = b;
    }

    /**
     * this is used to get the operation in the request object
     * @return return the value of the operation
     */

    public String getOp() {
        return op;
    }

    /**
     *  this is used to set the operation in the request object
     * @param op is the operation that will be introduced in the request object field
     */

    public void setOp(String op) {
        this.op = op;
    }

    /**
     * This constructs the request made to the REST server
     * @param a is the first number that will be used to make the operation
     * @param b is the second number that will be used to make the operation
     * @param op is the math operation to do using the two numbers referred before
     */

    public Request(double a, double b, String op) {
        this.a = a;
        this.b = b;
        this.op = op;
    }

    /**
     * Necessary to the json parser
     */

    public Request() {
    }

}
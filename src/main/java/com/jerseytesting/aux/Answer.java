package com.jerseytesting.aux;

public class Answer {
    private String operation;
    private double result;
    private String date;

    /**
     * this is used to get the operation done
     * @return the operation
     */

    public String getOperation() {
        return operation;
    }

    /**
     * this is to set the operation done in the answer object field
     * @param operation is the operation done
     */

    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * this is used to get the date of the operation done
     * @return the date of the operation
     */

    public String getDate() {
        return date;
    }

    /**
     * this is to set the date in the answer object field
     * @param date is the date when the operation was done
     */

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * this is used to get the result of the operation done
     * @return the result of the operation
     */

    public double getResult() {
        return result;
    }

    /**
     * this is to set the result of the operation in the answer object field
     * @param result is the result of the operation
     */

    public void setResult(double result) {
        this.result = result;
    }

    /**
     * This constructs the answer sent to the client
     * @param operation is the operation done
     * @param result is the result of the operation
     * @param date is the date when the operation was done
     */

    public Answer(String operation, double result, String date) {

        this.operation = operation;
        this.result = result;
        this.date = date;

    }

    /**
     * Necessary to return the json data to the client
     */

    public Answer() {

    }


}

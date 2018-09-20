package com.jerseytesting.aux;


import javax.persistence.*;


@Entity
@Table(name = "Answer")
public class Answer {

    private int answerId;
    private double result;
    private String operation;
    private String date;


    /**
     * this is used to get the Id
     * @return the Id
     */

    /*@Id
    @Column(name="answer_id")
    public int getAnswerId(){
        return answerId;
    }*/

    /**
     * this is to set the answer Id in the answer object field
     * @param answerId is the Id of the answer
     */

    /*public void setAnswerId(int answerId){
        this.answerId = answerId;
    }*/

    /**
     * this is used to get the operation done
     * @return the operation
     */

    @Column(name="operation")
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

    @Column(name="date")
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

    @Column(name="result")
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

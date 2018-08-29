package com.jerseytesting.webapp;


public class MyThread extends Thread {
    private Request request;


    public MyThread(Request request)
    {
        this.request = request;

    }


    @Override
    public void run()
    {
        RESTClientPost.requestTreatment(request);
    }
}
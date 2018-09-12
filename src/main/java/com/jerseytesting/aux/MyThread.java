package com.jerseytesting.aux;


import com.jerseytesting.rest.RESTClientPost;

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
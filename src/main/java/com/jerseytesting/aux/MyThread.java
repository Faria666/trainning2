package com.jerseytesting.aux;


import com.jerseytesting.rest.RESTClientPost;

import java.sql.SQLException;

public class MyThread extends Thread {
    private Request request;


    public MyThread(Request request)
    {
        this.request = request;

    }


    @Override
    public void run()
    {
        try {
            RESTClientPost.requestTreatment(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
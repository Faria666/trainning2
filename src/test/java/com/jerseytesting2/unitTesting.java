package com.jerseytesting2;

import com.jerseytesting2.rest.RESTServer;
import org.junit.Assert;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.io.IOException;
import org.mockito.Mockito;



public class unitTesting {


    @Test
    public void testRequestAcceptAdd() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"add"+"\""+"}";
        RESTServer server = Mockito.mock(RESTServer.class);
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept2() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"mult"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept3() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"div"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept4() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"avg"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

}
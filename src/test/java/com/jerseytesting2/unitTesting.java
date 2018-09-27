package com.jerseytesting2;

import com.jerseytesting2.aux.Answer;
import com.jerseytesting2.aux.Request;
import com.jerseytesting2.rest.RESTServer;
import org.junit.Assert;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.io.IOException;


public class unitTesting {

    @Test
    public void testRequest(){
        boolean state = false;
        Request req = new Request(14.23, 56.96, "add");
        if(14.23 == req.getValue1() && 56.96 == req.getValue2() && "add".equals(req.getOperation()))
            state = true;
        Assert.assertTrue(state);
    }

    @Test
    public void testRequestAccept() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"add"+"\""+"}";
        RESTServer server = new RESTServer();
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

    @Test
    public void testRequestAccept5() throws IOException {
        String request = "{"+ "\"" + "value1"+"\""+":"+"\""+"12"+"\""+", "+"\""+"value2"+"\""+":"+"\""+"67"+"\""+", "+"\""+"operation"+"\""+":"+"\""+"adkgukgd"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());

    }

    @Test
    public void testAnswer(){
        boolean state = false;
        Answer ans = new Answer();
        if(ans.getOperation() == null && ans.getDate() == null && ans.getResult() == 0.0) {
            state = true;
        }
        Assert.assertTrue(state);
    }

    @Test
    public void testAnswer1(){
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        Assert.assertEquals("add",ans.getOperation());
    }

    @Test
    public void testAnswer2(){
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        Assert.assertEquals("01/08/2018 11:01:42",ans.getDate());
    }

    @Test
    public void testAnswer3(){
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        double aux = ans.getResult();
        boolean state = false;
        if (845.45 == aux)
            state = true;
        Assert.assertTrue(state);
    }

    @Test
    public void testAnsSetGet(){
        String aux = "fhgf";
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        ans.setOperation(aux);
        Assert.assertEquals(aux, ans.getOperation());
    }

    @Test
    public void testAnsSetGet2(){
        String aux = "fhgf";
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        ans.setDate(aux);
        Assert.assertEquals(aux, ans.getDate());
    }

    @Test
    public void testAnsSetGet3(){
        double aux = 456.123;
        Answer ans = new Answer("add", 845.45, "01/08/2018 11:01:42");
        ans.setResult(aux);
        double aux2 = ans.getResult();
        boolean state = false;
        if (aux2 == aux)
            state = true;
        Assert.assertTrue(state);
    }

    /*@Test
    public void testWatchDirectoryAccept(){
        boolean flag = true;
        String directory = "/home/joao-faria/Desktop/jerseytesting/src/test/testDirectoryIn" ;
        ArrayList<String> filesexpected = new ArrayList<>();
        filesexpected.add("heyhey.csv");
        filesexpected.add("heyhow.csv");
        filesexpected.add("heyhoy.csv");
        ArrayList<String> temp = RESTClientPost.watchDirectory(directory);
        for(int i = 0; i< temp.size(); i++) {
            if (flag == false)
                break;
            else{
                if (filesexpected.contains(temp.get(i)))
                    flag = true;
                else
                    flag = false;
            }
        }

        Assert.assertTrue(flag);

    }

    @Test
    public void testWatchDirectoryFail(){
        boolean flag = true;
        String directory = "/home/joao-faria/Desktop/jerseytesting/src/test/testDirectoryIn/" ;
        ArrayList<String> filesexpected = new ArrayList<>();
        filesexpected.add("ukuku.csv");
        ArrayList<String> temp = RESTClientPost.watchDirectory(directory);
        for(int i = 0; i< temp.size(); i++){
            if(flag == false)
                break;
            else{
                if (filesexpected.contains(temp.get(i)))
                    flag = true;
                else
                    flag = false;
            }
        }

        Assert.assertFalse(flag);

    }

    @Test
    public void testQueueAccept(){
        Request req = new Request(12.0,0.1245, "add");
        Request aux;
        ArrayList<Request> request = new ArrayList<>();
        request.add(req);
        NewQueue queue;
        queue = RESTClientPost.createQueue(request);
        aux = queue.getObject();
        if(aux.getA() == req.getA() && aux.getB() == req.getB() && aux.getOp().compareTo(req.getOp())==0)
            Assert.assertTrue(true);
        else
            Assert.assertFalse(false);
    }


    @Test
    public void testStringToReq(){
        ArrayList<String> str = new ArrayList<>();
        ArrayList<Request> req;
        String a1 = "21,34";
        String a2 = ",";
        String a3 = "45,02";
        String a4 = ",add";
        String a5 = "\n";
        str.add(a1);
        str.add(a2);
        str.add(a3);
        str.add(a4);
        str.add(a5);
        req = RESTClientPost.stringToReq(str);
        Request aux = req.get(0);
        if(a1.equals(String.valueOf(aux.getA())) && a3.equals(String.valueOf(aux.getB())) && a4.equals(aux.getOp()))
            Assert.assertTrue(true);
    }*/

}
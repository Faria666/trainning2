package com.jerseytesting.aux;

import com.jerseytesting.rest.RESTClientPost;
import com.jerseytesting.rest.RESTServer;
import org.junit.Assert;
import org.junit.Test;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class RESTTest {

    @Test
    public void testRequest(){
        boolean state = false;
        Request req = new Request(14.23, 56.96, "add");
        if(14.23 == req.getA() && 56.96 == req.getB() && "add".equals(req.getOp()))
            state = true;
        Assert.assertTrue(state);
    }

    @Test
    public void testRequestAccept() {
        String request = "{"+ "\"" + "a"+"\""+":"+"\""+"12"+"\""+", "+"\""+"b"+"\""+":"+"\""+"67"+"\""+", "+"\""+"op"+"\""+":"+"\""+"add"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept2() {
        String request = "{"+ "\"" + "a"+"\""+":"+"\""+"12"+"\""+", "+"\""+"b"+"\""+":"+"\""+"67"+"\""+", "+"\""+"op"+"\""+":"+"\""+"mult"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept3() {
        String request = "{"+ "\"" + "a"+"\""+":"+"\""+"12"+"\""+", "+"\""+"b"+"\""+":"+"\""+"67"+"\""+", "+"\""+"op"+"\""+":"+"\""+"div"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept4() {
        String request = "{"+ "\"" + "a"+"\""+":"+"\""+"12"+"\""+", "+"\""+"b"+"\""+":"+"\""+"67"+"\""+", "+"\""+"op"+"\""+":"+"\""+"avg"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestAccept5() {
        String request = "{"+ "\"" + "a"+"\""+":"+"\""+"12"+"\""+", "+"\""+"b"+"\""+":"+"\""+"67"+"\""+", "+"\""+"op"+"\""+":"+"\""+"adkgukgd"+"\""+"}";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestFail2() {
        String request = "olyjh";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());

    }

    @Test
    public void testRequestFail3(){
        String request = "";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());
    }

    @Test
    public void testRequestFail4(){
        String request = null;
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());
    }

    @Test
    public void testRequestFail5(){
        String request = "12345";
        RESTServer server = new RESTServer();
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(),response.getStatus());
    }

    @Test
    public void testRequestFail6(){
        String request = " ";
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

    @Test
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

}
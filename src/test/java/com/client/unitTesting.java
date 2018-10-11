package com.client;

import com.calculator.service.Calculator;


public class unitTesting {

    Calculator server = new Calculator();

/*
    @Test
    public void testRestServer() throws IOException {
        String request = "{" + "\"" + "value1" + "\"" + ":" + "\"" + "12" + "\"" + ", " + "\"" + "value2" + "\"" + ":" + "\"" + "67" + "\"" + ", " + "\"" + "operation" + "\"" + ":" + "\"" + "add" + "\"" + "}";
        Response response = server.postRequest(request);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    }

    @Test
    public void testConvertToObj() throws IOException {
        boolean flag = false;
        Request request;
        String json = "{" + "\"" + "value1" + "\"" + ":" + "\"" + "12" + "\"" + ", " + "\"" + "value2" + "\"" + ":" + "\"" + "67" + "\"" + ", " + "\"" + "operation" + "\"" + ":" + "\"" + "add" + "\"" + "}";
        request = server.convertToObj(json);
        if((request.getValue1()==12) && (request.getValue2()==67) && (request.getOperation().compareToIgnoreCase("add")==0))
           flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testConvertToJson() throws JsonProcessingException {
        boolean flag = false;
        Answer answer = new Answer("add",67.0,"12/12/2012");
        String json;
        json = server.convertToJson(answer,"something just for the log");
        if(json.contains("add") && json.contains("67.0") && json.contains("12/12/2012"));
            flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testCalculatorAdd(){
        boolean flag = false;
        Request request = new Request(10,5,"add");
        if(server.calculator(request) == 15)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorMult(){
        boolean flag = false;
        Request request = new Request(10,5,"mult");
        if(server.calculator(request) == 50)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorDiv(){
        boolean flag = false;
        Request request = new Request(10,5,"div");
        if(server.calculator(request) == 2)
            flag = true;
        Assert.assertTrue(flag);

    }

    @Test
    public void testCalculatorAvg(){
        boolean flag = false;
        Request request = new Request(10,5,"avg");
        if(server.calculator(request) == 7.5)
            flag = true;
        Assert.assertTrue(flag);

    }


    @Test
    public void testBuildAnswer() {
        boolean flag = false;
        double result = 15;
        String operation = "add";
        Answer answer;
        answer = Calculator.buildAnswer(operation, result);
        if((answer.getOperation().compareToIgnoreCase(operation) == 0) && answer.getResult() == 15)
            flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testReadFileWithFramework() throws IOException {
        boolean flag = false;
        String file = "test.csv";
        ArrayList<Request> requestList;
        ArrayList<String> files = new ArrayList<>();
        files.add(file);

        File oldfile =new File("/home/joao-faria/Desktop/client/src/test/java/com/client/watchTest/Done - test.csv");
        File newfile =new File("/home/joao-faria/Desktop/client/src/test/java/com/client/watchTest/test.csv");

        if(oldfile.renameTo(newfile)){
            System.out.println("Rename succesful");
        }else{
            System.out.println("Rename failed");
        }

        Client client = mock(Client.class);
        requestList = client.readFileWithFramework("/home/joao-faria/Desktop/client/src/test/java/com/client/watchTest/", "/home/joao-faria/Desktop/client/src/test/java/com/client/watchTest/", files);
        if(requestList.get(0).getValue1() == 21.34 && requestList.get(0).getValue2( )== 45.02 && requestList.get(0).getOperation().compareToIgnoreCase("add")==0)
            flag = true;


        Assert.assertTrue(flag);
    }

    @Test
    public void testSeekFiles(){
        boolean flag = false;
        ArrayList<String> files;
        Client client = mock(Client.class);
        files = client.seekFiles("/home/joao-faria/Desktop/client/src/test/java/com/client/seekTest/");
        if(files.get(0).compareToIgnoreCase("Done - test.csv")==0)
            flag = true;
        Assert.assertTrue(flag);
    }


    @Test
    public void testCreateQueue() throws InterruptedException {
        boolean flag = false;
        ArrayList<Request> requests = new ArrayList<>();
        Request request = new Request(10, 5, "add");
        Request auxiliary;
        requests.add(request);
        Client client = mock(Client.class);
        ArrayBlockingQueue blockingQ = client.createQueue(requests);
        auxiliary = (Request) blockingQ.take();
        if(auxiliary.getValue1() == 10 && auxiliary.getValue2() == 5 && auxiliary.getOperation().compareToIgnoreCase("add")==0)
            flag = true;
        Assert.assertTrue(flag);
    }

    @Test
    public void testWatchDirectory(){
        boolean flag = false;
        FileFunctions file = new FileFunctions("/home/joao-faria/Desktop/client/src/test/java/com/client/wdTest/newfile.csv");
        Client client = mock(Client.class);
        when(client.watchDirectory("/home/joao-faria/Desktop/client/src/test/java/com/client/wdTest/")).thenReturn(flag = true);
        Assert.assertTrue(flag);
    }*/
}
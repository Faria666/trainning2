package com.jerseytesting.webapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import org.apache.commons.csv.CSVFormat;
import java.nio.file.Paths;

import static org.eclipse.jetty.util.ArrayUtil.add;

public class RESTClientPost {

    private static final Logger log = LoggerFactory.getLogger(RESTClientPost.class);

    private static final String QUOTATION_DELIMITER = "\"";

    protected static ArrayList<String> readCSV(ArrayList<String> files, String inputDirectory, String outputDirectory){

        Scanner scanner = null;
        ArrayList<String> requests = new ArrayList<>();

        for(int i = 0; i< files.size();i++){
            try {

                scanner = new Scanner(new File(inputDirectory + files.get(i)));

                scanner.useDelimiter(QUOTATION_DELIMITER);

                while(scanner.hasNext())
                {
                    requests.add(scanner.next());

                }
            }
            catch (FileNotFoundException fe)
            {
                fe.printStackTrace();
            }
            finally
            {
                scanner.close();

                try{

                    File afile =new File(inputDirectory + files.get(i));

                    if(afile.renameTo(new File(outputDirectory + "Done - " + afile.getName()))){
                        System.out.println("File is moved successful!");
                    }else{
                        System.out.println("File is failed to move!");
                    }
                    log.debug("Reading file:", afile.getName());

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return requests;
    }

    protected static ArrayList<Request> stringToReq(ArrayList<String> data){
        Request req;
        ArrayList<Request> request = new ArrayList<>();
        String aux;
        String[] splitted = new String[0];
        String[] toConvert;
        String[] noComma;
        int count;


        //split by the \n
        for(int i = 0; i<data.size();i++){
            aux = data.get(i);
            splitted = add(splitted, aux.split("\n"));
        }


        noComma = new String[splitted.length];
        count = 0;

        for(int i = 0; i<splitted.length; i++){
            if (splitted[i].compareTo(",") != 0){
                noComma[count] = splitted[i];
                count++;
            }
        }

        toConvert = new String[noComma.length];
        count = 0;


        //remove char , from the operation field
        for(int k = 0 ; k<noComma.length;k++){
            if(noComma[k] != null) {
                if (noComma[k].charAt(0) == ',') {
                    toConvert[count] = noComma[k].substring(1);
                    count++;
                } else {
                    toConvert[count] = noComma[k];
                    count++;
                }
            }

        }

        //convert data to Request object
        for(int i =0; i<toConvert.length;i=i+3) {
            if(toConvert[i] != null) {
                DecimalFormat decimalformat = new DecimalFormat("#");
                double temp1 = 0;
                try {
                    temp1 = decimalformat.parse(toConvert[i]).doubleValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                double temp2 = 0;
                try {
                    temp2 = decimalformat.parse(toConvert[i+1]).doubleValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String temp3 = toConvert[i+2];
                req = new Request(temp1, temp2, temp3);
                request.add(req);
            }
        }
        return request;
    }

    protected static ArrayList<String> watchDirectory(String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> files = new ArrayList<>();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files.add(listOfFiles[i].getName());
                System.out.println("File " + listOfFiles[i].getName());
                log.debug("Processing file:" + listOfFiles[i].getName());
            }
        }
        return files;
    }

    protected static ArrayList<Request> readFileWithFramework(String inputDirectory, String outputDirectory, ArrayList<String> files) throws IOException {

        Request req;
        ArrayList<Request> requests = new ArrayList<>();

        for(int i = 0; i< files.size();i++) {
            try {

                try (
                        Reader reader = Files.newBufferedReader(Paths.get(inputDirectory + files.get(i)));
                        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                ) {
                    for (CSVRecord csvRecord : csvParser) {

                        // Accessing Values by Column Index
                        String val1 = csvRecord.get(0);
                        String val2 = csvRecord.get(1);
                        String op = csvRecord.get(2);

                        DecimalFormat decimalformat = new DecimalFormat("#");
                        double temp1 = 0;
                        try {
                            temp1 = decimalformat.parse(String.valueOf(val1)).doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        double temp2 = 0;
                        try {
                            temp2 = decimalformat.parse(String.valueOf(val2)).doubleValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String temp3 = op;
                        req = new Request(temp1, temp2, temp3);
                        requests.add(req);
                    }
                }
            } finally {

                try {

                    File afile = new File(inputDirectory + files.get(i));

                    if (afile.renameTo(new File(outputDirectory + "Done - " + afile.getName()))) {
                        System.out.println("File is moved successful!");
                    } else {
                        System.out.println("File is failed to move!");
                    }
                    log.debug("Reading file:", afile.getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return requests;
    }

    protected static ArrayList<Request> processFiles(String inputDirectory, String outputDirectory){
        ArrayList<String> files;
        ArrayList<Request> requests = new ArrayList<>();

        while(true){

            files = watchDirectory(inputDirectory);

            try {
                requests.addAll(readFileWithFramework(inputDirectory, outputDirectory, files));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return requests;
        }
    }

    protected static NewQueue createQueue(ArrayList<Request> requests){

        NewQueue queue = new NewQueue();
        
        for(int i = 0; i < requests.size(); i++){
            queue.putObject(requests.get(i));
        }
        return queue;
    }

    protected static void requestTreatment( Request request){
        Answer answer = new Answer();

        /*String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        answer.setOperation("none");
        answer.setResult(0);
        answer.setDate(date);*/


        if(request != null)
            answer = client(request);
        if(answer.getOperation().compareTo("none")!=0 ) {
            JDBCConnection(answer, request);
        }
    }

    protected static Answer client(Request req){

        ObjectMapper mapper = new ObjectMapper();
        String request = null;
        String ans;
        String location = "/calc";
        String URI = "http://localhost:8080/calculator";
        Answer answer = new Answer();

        try {
            request = mapper.writeValueAsString(req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Client client = ClientBuilder.newClient();

        WebTarget target = client.target(URI);

        WebTarget answerWebTarget;

        answerWebTarget = target.path(location);

        Invocation.Builder invocationBuilder;

        invocationBuilder = answerWebTarget.request(MediaType.APPLICATION_JSON);

        Response response = invocationBuilder.post(Entity.entity(request,MediaType.APPLICATION_JSON));

        /*System.out.print("\nStatus: ");

        System.out.println(response.getStatus());*/

        ans = response.readEntity(String.class);

        System.out.println(ans + "\n");

        try {
            answer = mapper.readValue(ans, Answer.class);
        } catch (IOException e) {
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            answer.setOperation("none");
            answer.setResult(0);
            answer.setDate(date);
        }
        log.debug("Request processed, answer: {} {} {}", answer.getOperation(), answer.getResult(), answer.getDate());
        return answer;
    }

    private static void JDBCConnection(Answer answer, Request request) {

        String jdbc = "jdbc:postgresql://localhost:5432/jpfar";
        String username = "jpfar";
        String password = "jpfar";

        int id = 0;

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement ps;

        try {

            conn = DriverManager.getConnection(jdbc, username, password);


            stmt = conn.createStatement();
            //--------------------SELECT----------------------
            ResultSet rs = stmt.executeQuery("SELECT max(idr) from requests");
            if(rs.next()) {
                id = rs.getInt(1);
                System.out.println(id);
            }

            id++;

            //--------------------INSERT----------------------
            log.debug("Inserting request: {} {} {} {} into database", id, request.getA(), request.getB(), request.getOp());
            log.debug("Inserting answer: {} {} {} {} into database", id,  answer.getOperation(), answer.getResult(), answer.getDate());

            ps = conn.prepareStatement("INSERT INTO requests(idr, val1, val2, op) VALUES(?,?,?,?)");
            ps.setInt(1,id);
            ps.setDouble(2,request.getA());
            ps.setDouble(3,request.getB());
            ps.setString(4, request.getOp());
            ps.execute();

            ps = conn.prepareStatement("INSERT INTO answers(ida, op, res, dat) VALUES(?,?,?,?)");
            ps.setInt(1,id);
            ps.setString(2,answer.getOperation());
            ps.setDouble(3,answer.getResult());
            ps.setString(4, answer.getDate());
            ps.execute();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {

                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String inputDirectory = "/home/joao-faria/Desktop/jerseytesting/files/input/";
        String outputDirectory = "/home/joao-faria/Desktop/jerseytesting/files/output/";
        NewQueue queue;

        ArrayList<Request> requests;
        int threads = 5;


        do {

            requests = processFiles(inputDirectory, outputDirectory);

            queue = createQueue(requests);

            for (int i = 0; i < threads; i++) {
                Request request = queue.getObject();
                if (request != null) {
                    MyThread thread = new MyThread(request);
                    thread.start();

                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } while (true);
    }
}

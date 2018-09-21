package com.jerseytesting.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jerseytesting.aux.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.commons.csv.CSVFormat;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class RESTClientPost {

    private static final Logger log = LoggerFactory.getLogger(RESTClientPost.class);

    /**
     * Function to for changes in a determinate directory
     * @param directory is the directory where to look for the changes
     * @return return a boolean that is true if have changes or false if not
     */

    private static boolean watchDirectory(final String directory){

        boolean flag = false;

        Path path = Paths.get(directory);
        try {
            // get watch service which will monitor the directory
            WatchService watcher = path.getFileSystem().newWatchService();
            // associate watch service with the directory to listen to the event types
            path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            System.out.println("\nMonitoring directory for changes...");
            // listen to events
            WatchKey watchKey = watcher.take();
            // get list of events as they occur
            List<WatchEvent<?>> events = watchKey.pollEvents();

            for (WatchEvent event : events) {
                flag = false;
                //check if the events refers to a new file created
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    flag = true;

                    System.out.println("Created: " + event.context().toString() + " ;");
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * Function to pick the name of files in a determinate directory
     * @param directory is the directory where to look for the files
     * @return return an array with the name of all files found in that directory
     */

    public static ArrayList<String> seekFiles(final String directory) {
        final File folder = new File(directory);
        final File[] listOfFiles = folder.listFiles();
        final ArrayList<String> files = new ArrayList<>();

        assert listOfFiles != null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                files.add(listOfFiles[i].getName());
                System.out.println("File " + listOfFiles[i].getName());
                log.debug("Processing file:" + listOfFiles[i].getName());
            }
        }
        return files;
    }

    /**
     * Function that will read from the files, transform the data in Request objects and finally move the treated file to other directory
     * @param inputDirectory is the directory where the new files will appear
     * @param outputDirectory is the directory where the files already readed will be moved to
     * @param files is the array with the name of the files found in the input directory
     * @return return an array of Request objects
     */

    private static ArrayList<Request> readFileWithFramework(final String inputDirectory, final String outputDirectory, final ArrayList<String> files) throws IOException {

        Request request;
        final ArrayList<Request> requestList = new ArrayList<>();

        for(int i = 0; i< files.size();i++) {
            try {

                try (
                        Reader reader = Files.newBufferedReader(Paths.get(inputDirectory + files.get(i)));
                        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
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
                        request = new Request(temp1, temp2, op);
                        requestList.add(request);
                    }
                }
            } finally {

                try {

                    File afile = new File(inputDirectory + files.get(i));

                    if (afile.renameTo(new File(outputDirectory + "Done - " + afile.getName()))) {
                        System.out.println("File is moved successfully!");
                    } else {
                        System.out.println("File is failed to move!");
                    }
                    log.debug("Reading file:", afile.getName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return requestList;
    }

    /**
     * Function that calls the function that is always looking for new files in the directory and the function that will read and move those files
     * @param inputDirectory is the directory where the new files will appear
     * @param outputDirectory is the directory where the readed files will be moved to
     * @return returns the list of the requests that were in the files
     */

    private static ArrayList<Request> processFiles(final String inputDirectory, final String outputDirectory){
        final ArrayList<String> files;
        final ArrayList<Request> requestList = new ArrayList<>();


        files = seekFiles(inputDirectory);

        try {
            requestList.addAll(readFileWithFramework(inputDirectory, outputDirectory, files));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestList;

    }

    /**
     * Creates and populates the queue composed by all the requests read from de CSV files
     * @param requestList is the Requests objects that contains all the info necessary for the server calculate and send back the answer
     * @return returns the queue already populated
     */

    private static ArrayBlockingQueue createQueue(final ArrayList<Request> requestList){

        final ArrayBlockingQueue queue = new ArrayBlockingQueue(1024);

        for(int i = 0; i < requestList.size(); i++){
            Producer producer = new Producer(queue,requestList.get(i));
            new Thread(producer).start();
        }
        return queue;
    }


    /**
     * Function that will be running in every thread created
     * @param request is the request to be treated by each thread, that sent it to the server
     */

    public static void requestTreatment(final Request request){
        Answer answer = new Answer();
        
        if(request != null)
            answer = client(request);
        if(answer.getOperation().compareTo("none")!=0 ) {
            insertJDBC(answer, request);
        }
    }

    /**
     * Connects to the REST server and send a request waiting for the response
     * @param requestObject is the request sent to the server to calculate
     * @return returns the answer in the form of a Answer object
     */

    private static Answer client(final Request requestObject){

        final ObjectMapper mapper = new ObjectMapper();
        String request = null;
        final String answer;
        final String location = "/calc";
        final String URI = "http://localhost:8080/calculator";//...localhost:8080...
        Answer answerObject = new Answer();



        try {
            request = mapper.writeValueAsString(requestObject);
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

        System.out.print("\nStatus: ");

        System.out.println(response.getStatus());

        answer = response.readEntity(String.class);

        System.out.println(answer + "\n");

        try {
            answerObject = mapper.readValue(answer, Answer.class);
        } catch (IOException e) {
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            answerObject.setOperation("none");
            answerObject.setResult(0);
            answerObject.setDate(date);
        }
        log.debug("Request processed, answer: {} {} {}", answerObject.getOperation(), answerObject.getResult(), answerObject.getDate());
        return answerObject;
    }

    /**
     * Connects to the database and proceed to the insertion of the data in the tables
     * @param answer is the answer received from the server
     * @param request is the request made to the server
     */

    private static void insertJDBC(final Answer answer, final Request request){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jerseytesting-JPA");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(answer);
        em.persist(request);
        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args){

        boolean changes;
        final String inputDirectory = "/home/joao-faria/Desktop/jerseytesting2/files/input/";
        final String outputDirectory = "/home/joao-faria/Desktop/jerseytesting2/files/output/";
        ArrayBlockingQueue queue;

        ArrayList<Request> requestList;
        int threads = 5;


        do {
            changes = watchDirectory(inputDirectory);

            requestList = processFiles(inputDirectory, outputDirectory);

            queue = createQueue(requestList);

            for (int i = 0; i < threads; i++) {
                Consumer consumer = new Consumer(queue);
                new Thread(consumer).start();
            }
        } while (changes);
    }
}

package com.client.others;

import com.client.service.Client;
import com.types.Request;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class FileFunctions {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    /**
     * Function that will read from the files, transform the data in Request objects and finally move the treated file to other directory
     * @param inputDirectory is the directory where the new files will appear
     * @param outputDirectory is the directory where the files already readed will be moved to
     * @param files is the array with the name of the files found in the input directory
     * @return return an array of Request objects
     */

    private static ArrayList<Request> readFileWithFramework(final String inputDirectory, final String outputDirectory, final String failDirectory, final ArrayList<String> files) throws IOException {

        Request request;
        final ArrayList<Request> requestList = new ArrayList<>();
        String fileExtension;

        for (String file : files) {

            fileExtension = file.substring(file.length() - 3);

            if (fileExtension.compareToIgnoreCase("csv") == 0) {
                try {

                    try (
                            Reader reader = Files.newBufferedReader(Paths.get(inputDirectory + file));
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

                        File afile = new File(inputDirectory + file);
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        if (afile.renameTo(new File(outputDirectory + afile.getName() + " (" + timestamp + ")"))) {
                            System.out.println("File is moved successfully!");
                        } else {
                            System.out.println("File is failed to move!");
                        }
                        log.debug("Reading file:", afile.getName());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {

                System.out.println("Ficheiro não suportado!");

                try {

                    File afile = new File(inputDirectory + file);
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    if (afile.renameTo(new File(failDirectory + afile.getName() + " (" + timestamp + ")"))) {
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

    public static ArrayList<Request> processFiles(final String inputDirectory, final String outputDirectory, final String unsupportedDirectory){
        final ArrayList<String> files;
        final ArrayList<Request> requestList = new ArrayList<>();


        files = Directory.seekFiles(inputDirectory);

        try {
            requestList.addAll(readFileWithFramework(inputDirectory, outputDirectory, unsupportedDirectory, files));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestList;

    }

    static void invalidLines(Request request) throws IOException {
        String filename= "src/main/resources/files/invalid.txt";
        String phrase = "-->     " + request.getValue1() + "   |   " + request.getValue2() + "   |   " + request.getOperation() + "\n";
        FileWriter fw = new FileWriter(filename,true); //the true will append the new data
        fw.write(phrase);//appends the string to the file
        fw.close();
    }


    public FileFunctions() {
    }
}

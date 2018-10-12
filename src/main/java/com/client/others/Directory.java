package com.client.others;

import com.client.service.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class Directory {

    private static final Logger log = LoggerFactory.getLogger(Client.class);


    /**
     * Function to look for changes in a determinate directory
     * @param directory is the directory where to look for the changes
     * @return return a boolean that is true if have changes or false if not
     */

    public static boolean watchDirectory(final String directory){

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

                    System.out.println("Inserted: " + event.context().toString() + " ;");
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
        final java.io.File folder = new java.io.File(directory);
        final File[] listOfFiles = folder.listFiles();
        final ArrayList<String> files = new ArrayList<>();


        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    files.add(listOfFile.getName());
                    System.out.println("File " + listOfFile.getName());
                    log.debug("Processing file:" + listOfFile.getName());
                }
            }
        }
        return files;
    }


    public Directory() {
    }
}

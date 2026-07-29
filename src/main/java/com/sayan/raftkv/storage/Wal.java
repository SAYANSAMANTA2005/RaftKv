package com.sayan.raftkv.storage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayan.raftkv.models.LogEntry;

public class Wal {
    private String path;
    private BufferedWriter writer;
    private ObjectMapper mapper;

    public Wal(String path) throws IOException {
        this.path = path;
        writer = new BufferedWriter(new FileWriter(path, true));
        mapper = new ObjectMapper();
        // Create the file if it doesn't exist
    }

    public void append(LogEntry entry) throws IOException {
        /*
         * previous implementation of writing log entry to file
         * String line = entry.getIndex() + ":" + entry.getTerm() + ":" +
         * entry.getCommand();
         * writer.write(line);
         * writer.newLine();//go to next line after writing the log entry
         * writer.flush();
         */
        String json = mapper.writeValueAsString(entry);
        writer.write(json);// write the json string to file
        writer.newLine();// go to next line after writing the log entry
        writer.flush();

    }

    public List<LogEntry> readAll() throws IOException {
        List<LogEntry> logs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LogEntry entry = mapper.readValue(line, LogEntry.class);
                // jackson will automatically map the json string to LogEntry object
                logs.add(entry);
            }
        }
        return logs;
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}

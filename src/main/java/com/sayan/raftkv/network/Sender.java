package com.sayan.raftkv.network;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayan.raftkv.models.LogEntry;

//import java.Exception;

public class Sender {
    private ObjectMapper mapper;
    private Socket socket;
    private PrintWriter writer;

    public Sender(String ReciverIp, int port) throws IOException {
        // here Data--> is object of LogEntry class
        mapper = new ObjectMapper();

        System.out.println("Connecting to receiver on " + ReciverIp + ":" + port + "...");
        // socket takes string value for IP so converting to String
        socket = new Socket(ReciverIp, port);
        writer = new PrintWriter(socket.getOutputStream(), true);

    }

    public void Send(LogEntry entry) throws IOException {
        String json = mapper.writeValueAsString(entry);
        writer.println(json);
        System.out.println("Sent message: " + json);
    }

    public void close() throws IOException {
        socket.close();
        System.out.println("Sender connection closed.");
    }
}

/*
 * 
 * 
 * Behaviour of Sender ->
 * it sends a messgae to the destination {ip,port}
 * then exits the programme
 * 
 * 
 */

/*
 * Behaviour of Sender
 *
 * 1. Connects to the specified receiver (IP, Port).
 * 2. Converts the LogEntry object to JSON.
 * 3. Sends exactly one JSON message.
 * 4. Constructor finishes.
 *
 * Note:
 * Since socket and writer are local variables, this is NOT a continuous sender.
 * Even if socket.close() is omitted, no further messages can be sent using this
 * object because the socket reference is lost after the constructor returns.
 */
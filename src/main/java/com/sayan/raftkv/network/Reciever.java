package com.sayan.raftkv.network;

import java.io.*;
import java.net.*;
import java.lang.String;

import com.sayan.raftkv.RaftNode.RaftNode;
import com.sayan.raftkv.RaftNode.NodeState;
import com.sayan.raftkv.models.LogEntry;
import com.sayan.raftkv.node.SingleNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayan.raftkv.Config.GlobalVar;

public class Reciever {
    private int port;
    private String path;

    public Reciever(int port, String path) {
        this.port = port;
        this.path = path;
    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("Server started. Waiting for connection on port " + port + "...");

        // while (true) --> So that Receiver remains active always
        // during programme is not stopped forcefully

        while (true) {
            // Reciver Reamins active All time (during Programme is not stopped forcefully)
            System.out.println("Waiting for connection...");

            // Reciver Waits For a msg to Come at this Port
            Socket socket = serverSocket.accept();
            System.out.println("Client connected from: " + socket.getRemoteSocketAddress());

            // Handle each client in a separate thread so multiple nodes can send at the
            // same time
            new Thread(() -> {
                try {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    // take input from Sender as Json --> convert it to Object
                    String json;
                    ObjectMapper mapper = new ObjectMapper();

                    while ((json = reader.readLine()) != null) {
                        LogEntry entry = mapper.readValue(json, LogEntry.class);

                        // send this entry object to SINGLENODE class for
                        // WAL writing & KVStorage Update
                        SingleNode node = new SingleNode(path, entry);
                        node.writeToWall();
                        node.applyToKVStore();

                        System.out.println("Received message: " + json);
                        /*
                         * // if any msg is received then node is follower
                         * // RaftNode.nodeState = NodeState.FOLLOWER;
                         */

                    }
                    GlobalVar.msgRecived = true;

                    socket.close();
                    System.out.println("Client connection closed.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }
    /*
     * public static void main(String[] args) throws Exception {
     * int port = 8000;
     * String path = "wal.log";
     * Reciever reciever = new Reciever(port, path);
     * reciever.start();
     * }
     * 
     */

}

/*
 * Reciever recives the json format ,
 * a string ---> then it converts it to a object (by jackson's objectmapper)
 * 
 * 
 * 
 */

/*
 * 
 * Main(client) [sends req]--> reciver Recives msg in Json ()
 * 
 * ---> converts this in LogEntry object --->
 * sends SingleNode for Wal writing & KVStorage Update
 * --->
 * 
 */
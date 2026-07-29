package com.sayan.raftkv;

import java.io.IOException;

import com.sayan.raftkv.models.Command;
import com.sayan.raftkv.models.CommandType;
import com.sayan.raftkv.models.LogEntry;
import com.sayan.raftkv.network.Sender;
import com.sayan.raftkv.network.Reciever;
import com.sayan.raftkv.Config.Config;
import com.sayan.raftkv.Config.ConfigLoader;
import com.sayan.raftkv.RaftNode.RaftNode;

/*
now A single Server should ---> Recieve messages from a fixed port(config.port)

it Should also Send Requests To all of it's Peer Server(IP+ Reciving Port)

Example:
 node1(Port:5001)  [sends requests]---> node2(IP2,Port:5002),node3(IP3,Port:50003),...
 node2(Port: 5002) [sends requests]---> node1(IP1,Port:5001),node3(IP3,Port:5003),...
 node3(Port:5003)[sends requests]---> node1(IP1,Port:5001),node2(IP2,Port:5002),...


*/

/*
30/07/26 --> architechture

Main --> ConfigLoader --> Config ---> RaftNode -->



*/
public class Main {
    public static void main(String[] args) {
        try {
            String nodeConfigPath = args[0];// (args.length > 0) ? args[0] : "Config/node1.properties";
            Config config = ConfigLoader.load(nodeConfigPath, "Config/cluster.properties");
            // 1. Start Receiver server in a background thread so it listens for incoming
            // TCP connections
            RaftNode raftnode = new RaftNode(config);
            Thread receiverThread = new Thread(() -> {
                try {

                    raftnode.startReciver();
                    // Reciever reciever = new Reciever(config.port,config.walpath);
                    // reciever.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start();

            // Give Receiver thread a moment to bind the socket and start listening
            Thread.sleep(500);

            // 2. Sender connects to Receiver and transmits the LogEntry
            // i will implement this Sender part later
            // Sender sender = new Sender(config.hostip, config.port, entry);
            while (true) {
                // infinte no sending signal to peers
                raftnode.SendServer();
                System.out.println("All sends attempted.");
                Thread.sleep(3000); // Wait 3 seconds before next round of sends
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
 * new architechture --> main(acts like client )-->
 * sends command to SingleNode -->
 * single Nodes then writes to WAl & applies to KVStore
 * 
 * 
 * 
 * 
 * -------------------------
 * singleNode takes (string path,LogEntry entry) as input
 * so we need to create a LogEntry object & a string (file's path) to pass
 * to singleNode
 * 
 * and also i need one singleNOde's object to call the methodes of sinlgenode
 * class
 */
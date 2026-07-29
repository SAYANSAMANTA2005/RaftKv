package com.sayan.raftkv;

import java.io.IOException;

import com.sayan.raftkv.models.Command;
import com.sayan.raftkv.models.CommandType;
import com.sayan.raftkv.models.LogEntry;
import com.sayan.raftkv.node.SingleNode;

public class Main {
    public static void main(String[] args) {
        try {
            String path = "wal.log";
            Command command = new Command(CommandType.SET, "key2643764", "value1");
            LogEntry entry = new LogEntry(1, 11, command);
            SingleNode singlenode = new SingleNode(path, entry);

            singlenode.writeToWall();
            singlenode.applyToKVStore();

            System.out.println("Successfully wrote to WAL!");
        } catch (IOException e) {
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
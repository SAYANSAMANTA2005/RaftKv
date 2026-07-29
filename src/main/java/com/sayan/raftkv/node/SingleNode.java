package com.sayan.raftkv.node;

import java.io.IOException;
import com.sayan.raftkv.storage.KVStore;
import com.sayan.raftkv.storage.Wal;
import com.sayan.raftkv.Command;
import com.sayan.raftkv.LogEntry;

public class SingleNode {
    // entry object to store the log entry
    private LogEntry entry;
    // kvstore object to store the key-value pairs
    private KVStore kvstore;//
    private Wal wal;
    private Command command;

    public SingleNode(String path, LogEntry entry) throws IOException {
        this.entry = entry;
        this.command = entry.getCommand();
        this.wal = new Wal(path);
        this.kvstore = new KVStore();
    }

    public void writeToWall() {
        // append the log entry to wal pbject
        /* Wal takes --> LogEntry as input */
        try {
            wal.append(entry);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Successfully wrote to WAL!");

    }

    public void applyToKVStore() {
        // apply the command to the KV store's object
        /* kvstores -->takes Command as input */
        kvstore.apply(command);

    }

}

/*
 * Single NODE's working --->
 * Think of a restaurant
 * 
 * Imagine:
 * 
 * Customer
 * │
 * ▼
 * Manager
 * │
 * ┌───┴────┐
 * ▼ ▼
 * Kitchen Cashier
 * 
 * The customer doesn't talk directly to the kitchen or cashier.
 * 
 * The manager coordinates everything.
 * 
 * Similarly,
 * 
 * Client
 * │
 * ▼
 * SingleNode
 * │
 * ┌─┴────────────┐
 * ▼ ▼
 * WAL KVStore
 * 
 * 
 */

/*
 * client's order -->
 * LogEntry object 's type'-->{index,term,command}
 * command-->{type,key,value}
 * 
 * singleNode create a LogEntry's object--> wall append(LogEntry)
 * -->KVStore apply (Command)
 * 
 * 
 * 
 * singlenode's tasks --> taking input LogEntry from client
 */
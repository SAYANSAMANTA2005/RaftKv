package com.sayan.raftkv.RaftNode;

import java.io.IOException;

import com.sayan.raftkv.Config.Config;
import com.sayan.raftkv.Config.Peer;
import com.sayan.raftkv.models.Command;
import com.sayan.raftkv.models.CommandType;
import com.sayan.raftkv.models.LogEntry;
import com.sayan.raftkv.network.Reciever;
import com.sayan.raftkv.network.Sender;
import com.sayan.raftkv.RaftNode.NodeState;

public class RaftNode {
    Config config;
    NodeState nodeState;
    private long currentTerm;
    public int electionTimeout;// time period for candidate to become leader
    // double heartBeatTime;//time period for leader to send heartBeat

    public RaftNode(Config config) {
        this.config = config;
        // INITIALISE EACH NODE --> as FOLLOWER
        this.nodeState = NodeState.FOLLOWER;
        this.electionTimeout = 100000;// Math.random();//random election time for follower
    }

    public void becomeCandidate() {
        this.nodeState = NodeState.CANDIDATE;
    }

    public LogEntry generateCMD() {
        /*
         * Creates a dummy Command & dummy logentry for now
         */
        Command command = new Command(CommandType.SET, "key" + config.nodeId, "value" + config.hostip);
        LogEntry entry = new LogEntry(1, 11, command);
        return entry;
    }

    public void startReciver() {
        // it Creates Node's own Listener
        try {
            Reciever reciever = new Reciever(config.port, config.walpath);
            reciever.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// CR //

    public void SendServer() throws IOException {
        // i will implement this later
        // send 10 dummy msg to all peers
        for (Peer peer : config.peers.values()) {
            LogEntry entry = generateCMD();
            try {
                Sender sender = new Sender(peer.getHost(), peer.getPort());
                for (int i = 0; i < 10; i++) {
                    sender.Send(entry);
                }
                sender.close();
            } catch (java.net.ConnectException e) {
                System.out.println(
                        "Peer " + peer.getNodeId() + " (" + peer.getHost() + ":" + peer.getPort() + ") is offline.");
            }
        }

        // LogEntry entry = generateCMD();
        // Sender sender = new Sender(config.hostip, config.port, entry);

    }//
}//

/*
 * Each RaftNode has its own
 * ip
 * port
 * walpath(the file where log will be stored)
 * nodeId
 * 
 * list of Peers servers[hostip,port]
 * 
 * Now For the time being -->
 * RaftNode will also generates Command & LogEntry
 * 
 * --> then sends to Sender(Config node)
 * ---> Sender sends the Command to Other Node via TCP SOCKET
 * ---> Other RafNode will recieve The json(msg) & it Writes to Wal & store in
 * KVstorage
 * 
 * ReftNode ::
 * Tasks::
 * 1)generate Command & generate LogEntry [done]
 * 2)Send the LogEntry to designated Raftnode ---> it wll call Sender(Ip,Port)
 * 3)Creats a Recever Socket to Listen the incomming messages [done]
 * 
 */

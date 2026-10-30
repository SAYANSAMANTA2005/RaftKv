package com.sayan.raftkv.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Config {
    public int nodeId;
    public int port;
    public String hostip;
    public String walpath;
    public Peer self;// contains -->{nodeId,hostip,port}
    public Map<Integer, Peer> peers = new HashMap<>();
    // peer[nodeId]--> represents a peer,we will use this to get the IP and port of
    // the peer

    public static Config load(String Filename) throws IOException {// filename --> Config/node1 etc

        Config config = new Config();
        Properties props = new Properties();
        props.load(new FileInputStream(Filename));

        config.nodeId = Integer.parseInt(props.getProperty("nodeId"));
        config.port = Integer.parseInt(props.getProperty("port"));
        config.hostip = props.getProperty("hostip");
        config.walpath = props.getProperty("walpath");

        return config;

    }

    public Peer getSelf() {
        return this.self;
    }

    public void setSelf(Peer self) {
        this.self = self;
    }

    public void addPeer(Peer peer) {
        // handels the case when adding the self peer
        // in Peer class i have to add equal() operator overloading
        if (peer.equals(this.self))
            return;// u will not add yourself to the peers
        this.peers.put(peer.getNodeId(), peer);
    }

    public Peer getPeer(int nodeId) {
        return this.peers.get(nodeId);
    }

}

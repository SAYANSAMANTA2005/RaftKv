package com.sayan.raftkv.Config;

public class Peer {
    private int nodeId;
    private int port;
    private String host;// ip address of host server

    public Peer(int nodeId, String host, int port) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
    }

    public int getNodeId() {
        return nodeId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        // after this System.out.println(peer);-->prints like --> NodeId: 2 Host:
        // 127.0.0.1 Port: 5002
        return "NodeId: " + nodeId + " Host: " + host + " Port: " + port;
    }
    // implement is equal() function for Peer class

}

package com.sayan.raftkv.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RPC {

    private ServerSocket serversocket;
    private Integer port;

    public RPC(Integer port) throws IOException {
        this.port = port;
        this.serversocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        while (true) {
            // listen msg over network
            Socket socket = serversocket.accept();
        }
    }
}

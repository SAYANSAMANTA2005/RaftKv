package com.sayan.raftkv.Config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

    public static Config load(String nodeConfigPath, String clusterConfigPath) throws Exception {
        // Creating Config object
        // self properties of config
        Config config = Config.load(nodeConfigPath);

        // clusterProps property of config
        Properties clusterProps = new Properties();
        clusterProps.load(new FileInputStream(clusterConfigPath));

        //
        Properties selfProps = new Properties();
        selfProps.load(new FileInputStream(nodeConfigPath));

        // 4. Build self and peers <-- YOUR LOOP GOES HERE

        int myId = Integer.parseInt(selfProps.getProperty("nodeId"));

        int clusterSize = Integer.parseInt(clusterProps.getProperty("clusterSize"));

        for (int nodeId = 1; nodeId <= clusterSize; nodeId++) {
            // Peer peer;{nodeId,ip,Port}

            // creating Peer object for Each of the Peers
            String nodeIp = clusterProps.getProperty("node." + nodeId + ".host");
            int nodePort = Integer.parseInt(clusterProps.getProperty("node." + nodeId + ".port"));

            Peer peer = new Peer(nodeId, nodeIp, nodePort);

            if (nodeId == myId)
                config.setSelf(peer);// selfnode
            else {
                config.addPeer(peer);
            }

        }
        /////////////

        return config;

    }
}

/*
 * Config Loader ----> loads the config
 * 1)loads the self property of the node(port,hostIp,walpath,nodeId)
 * 
 * 2)loads the Peers property of the node(list of peers servers)
 * 
 * ConfigLoader(nodepath,clusterpath) ----> creates Config() class
 * 
 * Config has info -->(hostip,port,walpath)
 * Config.peers --> list of peers servers
 * 
 * WE Create instance of RaftNode class By Config Object
 * 
 * --->
 * RaftNode --> 1)Creates Receiver at Port
 * RaftNode --> 2)Tries to send message to Other Servers **[baki]**
 * 
 * --->Reciver --> Recieves Json --> Deserialize to LogEntry
 * --->Reciever --> Append to WAL
 * --->Reciever --> Apply to KVStorage
 * 
 * 
 * Sender --> Sends Message to Other Servers(config.hostip,config.port)
 * 
 * 
 */
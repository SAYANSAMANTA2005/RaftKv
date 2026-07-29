Main
 │
 ▼
ConfigLoader
 │
 ├── Read node1.properties
 ├── Read cluster.json
 ├── Create Peer(self)
 ├── Create Peer(other nodes)
 ├── Create StorageConfig
 ├── Create RaftConfig
 │
 ▼
Config
 │
 ▼
RaftNode










---------------------------------------------------------------
src/
└── main/
    └── java/
        └── com/
            └── sayan/
                └── raftkv/
                    ├── Main.java
                    ├── raft/
                    │    └── RaftNode.java
                    ├── config/
                    │    ├── Config.java
                    │    ├── Peer.java
                    │    ├── StorageConfig.java
                    │    ├── RaftConfig.java
                    │    └── ConfigLoader.java   <-- HERE
                    └── rpc/






----------------------------------------------------------------------------------

             ConfigLoader
                 │
                 ▼
             Config Object
                 │
      ┌──────────┴──────────┐
      ▼                     ▼
   RaftNode             RpcServer
      │                     │
      ▼                     ▼
  RpcClient              WAL
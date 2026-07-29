Node

├── RaftNode.java
├── RpcServer.java
├── RpcClient.java
├── Peer.java
├── rpc/
│     ├── RequestVoteRequest.java
│     ├── RequestVoteResponse.java
│     ├── AppendEntriesRequest.java
│     └── AppendEntriesResponse.java
└── state/



+-------------------------+
|      Raft Algorithm     |
| (Follower/Candidate/Leader)
+-------------------------+
            ▲
            │
+-------------------------+
|        RPC Layer        |
| RpcClient / RpcServer   |
+-------------------------+
            ▲
            │
+-------------------------+
|      TCP Sockets        |
| Socket / ServerSocket   |
+-------------------------+
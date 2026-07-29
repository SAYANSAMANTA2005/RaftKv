                         ┌──────────────────────┐
                         │      RaftNode        │
                         │  (Coordinator/Brain) │
                         └──────────┬───────────┘
                                    │
        ┌──────────────┬────────────┼─────────────┬───────────────┐
        │              │            │             │               │
        ▼              ▼            ▼             ▼               ▼
  RpcServer      RpcClient    ElectionTimer   HeartbeatTimer     KVStore
        │              │                           │               │
        │              │                           │               │
        └──────────────┼───────────────────────────┘               │
                       ▼                                           ▼
                   Other Nodes                              WAL + Storage


-----------------------------------------------------------------------------------------
raftkv/
│
├── Main.java
│
├── node/
│   ├── RaftNode.java
│   ├── NodeState.java
│   └── Peer.java
│
├── network/
│   ├── RpcServer.java
│   ├── RpcClient.java
│   └── MessageType.java
│
├── rpc/
│   ├── RequestVoteRequest.java
│   ├── RequestVoteResponse.java
│   ├── AppendEntriesRequest.java
│   ├── AppendEntriesResponse.java
│   └── RpcEnvelope.java
│
├── timer/
│   ├── ElectionTimer.java
│   └── HeartbeatTimer.java
│
├── storage/
│   ├── KVStore.java
│   ├── WAL.java
│   ├── LogEntry.java
│   └── Log.java
│
└── util/
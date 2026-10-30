# RaftKV Roadmap (Current Progress)

## ✅ Completed (Week 1)

### Storage
- [x] In-memory KVStore
- [x] CRUD Operations
- [x] WAL (Write Ahead Log)
- [x] WAL Recovery
- [x] LogEntry model

### Networking
- [x] TCP Sender
- [x] TCP Receiver
- [x] JSON Serialization (Jackson)

---

# 🚧 Week 2 (Leader Election)

## RaftNode
- [ ] RaftNode class
- [ ] Node configuration loader
- [ ] Peer discovery

## Node State
- [ ] FOLLOWER state
- [ ] CANDIDATE state
- [ ] LEADER state
- [ ] State transition methods

## Persistent State
- [ ] currentTerm
- [ ] votedFor

## Election
- [ ] Random election timeout
- [ ] Election timer
- [ ] Become Candidate
- [ ] RequestVote RPC
- [ ] Vote counting
- [ ] Become Leader

## Leader
- [ ] Heartbeat thread
- [ ] Periodic AppendEntries (Heartbeat)

## Follower
- [ ] Reset timer on heartbeat
- [ ] Grant / Reject votes
- [ ] Handle higher term

## RPC
- [ ] RequestVote Request
- [ ] RequestVote Response
- [ ] AppendEntries Request
- [ ] AppendEntries Response

---

# 🚧 Week 3 (Log Replication)

## Leader
- [ ] Append client commands to WAL
- [ ] Send AppendEntries with logs
- [ ] Maintain nextIndex[]
- [ ] Maintain matchIndex[]

## Followers
- [ ] Verify previous log index
- [ ] Verify previous log term
- [ ] Append replicated entries

## Commit
- [ ] commitIndex
- [ ] lastApplied
- [ ] Majority commit rule
- [ ] Apply committed logs to KVStore

## Conflict Resolution
- [ ] Delete conflicting entries
- [ ] Re-sync follower logs

---

# 🚧 Week 4 (Client Layer)

- [ ] Client API
- [ ] GET request
- [ ] SET request
- [ ] DELETE request
- [ ] Redirect writes to Leader
- [ ] Leader discovery

---

# 🚧 Week 5 (Recovery & Testing)

- [ ] Recover from WAL after restart
- [ ] Leader crash recovery
- [ ] Follower crash recovery
- [ ] Network partition testing
- [ ] Multi-node integration testing
- [ ] Performance testing

---

# Final Architecture

```text
                 Client
                    │
                    ▼
               Leader Node
                    │
        ┌───────────┴───────────┐
        ▼                       ▼
    Follower               Follower
        │                       │
       WAL                     WAL
        │                       │
     KVStore                KVStore
```

## Final Goal

A distributed Key-Value Store where:

- One Leader is elected automatically.
- Followers replicate the Leader's log.
- Data is committed after majority replication.
- Every node maintains the same KV state.
- The cluster automatically recovers from leader failures.

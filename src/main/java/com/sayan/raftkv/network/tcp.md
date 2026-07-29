# TCP Sender & Receiver — Unexpected Behavior Analysis

## What's happening (the timeline)

```
Node1 Sender                         Node2 Receiver
─────────────                        ────────────────
                                     ServerSocket(5002)
                                     while(true) {
                                       accept()  ← BLOCKS, waiting...
                                        │
new Socket(Node2:5002) ──────────────►  returns Socket ✔
                                        │
Send(msg1)  ──► writer.println(json) ──► readLine()  → reads msg1 ✔
Send(msg2)  ──► writer.println(json) ──►   │
Send(msg3)  ──► writer.println(json) ──►   │  (msg2, msg3 ... msg11
...                                    │   │   sit in the TCP buffer,
Send(msg11) ──► writer.println(json) ──►   │   NEVER read!)
                                        │
                                     socket.close()      ← closes client
                                     serverSocket.close() ← ⚠ KILLS THE SERVER
                                        │
                                     } // loop back
                                     accept()  → 💥 SocketException: Socket is closed
```

---

## Bug #1 — Receiver closes `ServerSocket` inside `while(true)`

### Where
`Reciever.java` — lines 50-51 inside the `while(true)` loop:
```java
socket.close();          // ← OK, closes the current client
serverSocket.close();    // ← ⚠ KILLS the server permanently
```

### What happens
1. Receiver accepts **one** client connection.
2. Reads **one** message, processes it.
3. Closes both the client socket AND the server socket.
4. Loops back to `accept()` → 💥 `SocketException: Socket is closed`
5. Receiver thread dies. Node can never receive again.

### Why it's wrong
`serverSocket.close()` shuts down the listener port entirely. Once closed, no future `accept()` call can work. The `while(true)` loop becomes useless because the server is dead after the first client.

### Fix
**Remove `serverSocket.close()` from the loop.** Only close the **client** socket:

```java
while (true) {
    Socket socket = serverSocket.accept();
    // ... read, process ...
    socket.close();          // ← close client connection only
    // DO NOT close serverSocket here
}
```

---

## Bug #2 — Sender sends 11 messages, Receiver reads only 1

### Where
**Sender** (`RaftNode.SendServer()`): sends 11 messages on one persistent TCP connection.
**Receiver** (`Reciever.start()`): calls `readLine()` exactly **once** per `accept()`.

### What happens
```
Sender sends:   msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11
                 ↓
Receiver reads:  msg1  ← only this one
                        msg2..msg11 are LOST (still in TCP buffer when socket closes)
```

### Why it's wrong
The Sender now has a persistent connection (`socket` and `writer` as fields) and sends 11 `writer.println()` calls. But the Receiver does a single `readLine()` and immediately closes the socket. The remaining 10 messages are silently discarded.

### Fix — Two options:

**Option A: Receiver reads in a loop (match the Sender)**
```java
while (true) {
    Socket socket = serverSocket.accept();
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(socket.getInputStream()));

    String json;
    while ((json = reader.readLine()) != null) {  // ← read ALL messages
        LogEntry entry = mapper.readValue(json, LogEntry.class);
        SingleNode node = new SingleNode(path, entry);
        node.writeToWall();
        node.applyToKVStore();
        System.out.println("Received: " + json);
    }

    socket.close();  // close client only, NOT serverSocket
}
```

**Option B: Sender opens a new connection per message (one-shot)**
```java
// In RaftNode.SendServer():
for (Peer peer : config.peers.values()) {
    LogEntry entry = generateCMD();
    // one connection, one message, close
    Sender sender = new Sender(peer.getHost(), peer.getPort());
    sender.Send(entry);
    sender.close();
}
```

> **Recommendation:** Option A is better for throughput. The Sender already has a persistent connection pattern — make the Receiver match it by reading in a loop until the Sender closes the connection (`readLine()` returns `null`).

---

## Summary Table

| Bug | Location | Symptom | Root Cause | Fix |
|-----|----------|---------|------------|-----|
| #1 | `Reciever.java:51` | `SocketException: Socket is closed` | `serverSocket.close()` inside `while(true)` | Remove `serverSocket.close()` from loop |
| #2 | `Reciever.java:38` | Only 1 of 11 messages received | Single `readLine()` per connection | Use `while ((json = readLine()) != null)` loop |

---

## Bonus: Race condition in `Main.java`

The `while(true)` in Main (line 61) calls `SendServer()` infinitely with no delay. This means:
- Node opens a connection, sends 11 messages, closes it, immediately reopens, sends 11 more, ...
- This is an infinite CPU-burning tight loop with no `Thread.sleep()`.
- Add at least `Thread.sleep(1000)` between rounds to avoid flooding.

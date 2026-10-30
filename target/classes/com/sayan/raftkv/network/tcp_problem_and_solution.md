# TCP Networking in RaftKV: Problems & Solutions

## Overview
This document details the issues encountered during the development of the TCP communication layer between `Main` (Client), `Sender`, and `Reciever` (Server), and explains why the applied fixes resolved them.

---

## 1. Unhandled Exception Error (`Unhandled exception type Exception`)

### The Cause
In Java, checked exceptions must be explicitly caught or declared in the method signature (`throws` clause).
- In `Main.java`, the code was originally catching `catch (IOException e)`.
- When `Reciever` declared `throws Exception`, the Java compiler flagged a compile error (`Unhandled exception type Exception`) because `Exception` is the base class of all checked exceptions and is broader than `IOException`. Catching `IOException` does not satisfy the compiler for general `Exception`.

### Why the Solution Worked
- **Targeted Exception Handling**: Changed `catch (IOException e)` to `catch (Exception e)` in [Main.java](file:///e:/Program%20Files/eclipse-workspace/raftkv/src/main/java/com/sayan/raftkv/Main.java#L38).
- **Refined Signatures**: Updated methods in [Reciever.java](file:///e:/Program%20Files/eclipse-workspace/raftkv/src/main/java/com/sayan/raftkv/network/Reciever.java#L16) and [Sender.java](file:///e:/Program%20Files/eclipse-workspace/raftkv/src/main/java/com/sayan/raftkv/network/Sender.java#L10) to declare exact exceptions like `IOException` or `Exception` consistently.

---

## 2. Blocking I/O Deadlock (`Connection refused`)

### The Cause
Java TCP socket servers use blocking I/O by default:
- `serverSocket.accept()` **blocks** thread execution until a client connects.
- Running `Reciever` sequentially on the main thread in `Main.java` before `Sender`:
  ```java
  Reciever reciever = new Reciever(port); // BLOCKS waiting for connection
  Sender sender = new Sender(ip, port, entry); // NEVER REACHED!
  ```
- Because `Reciever` blocked the single main thread, `Sender` was never executed, leading to thread deadlock or `java.net.ConnectException: Connection refused`.

### Why the Solution Worked
- **Asynchronous Execution**: Launched `Reciever` in a separate background thread within [Main.java](file:///e:/Program%20Files/eclipse-workspace/raftkv/src/main/java/com/sayan/raftkv/Main.java#L20):
  ```java
  Thread receiverThread = new Thread(() -> {
      try {
          Reciever reciever = new Reciever(port);
          reciever.start();
      } catch (Exception e) {
          e.printStackTrace();
      }
  });
  receiverThread.start();
  ```
- **Socket Initialization Delay**: Introduced a brief delay (`Thread.sleep(500)`) allowing `Reciever` to bind to port 8000 before `Sender` attempts to connect.
- **Successful Transmission**: `Sender` runs on the main thread, successfully connects to the background server socket, sends the Jackson-serialized `LogEntry`, and closes the socket cleanly.

---

## 3. Port & Host Mismatch

### The Cause
- `Sender` was configured with a remote IP (`192.168.0.100`) or hardcoded port numbers (5000) that did not match `Reciever`'s listening port (8000).

### Why the Solution Worked
- Standardized IP configuration to local loopback (`127.0.0.1`) and port (`8000`) across `Main`, `Sender`, and `Reciever`.

---

## Architecture Flowchart

```
┌────────────────────────────────────────────────────────────────────────┐
│                                Main.java                               │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
           ┌────────────────────────┴────────────────────────┐
           ▼                                                 ▼
┌─────────────────────────┐                       ┌──────────────────────┐
│ Background ServerThread │                       │     Main Thread      │
│ (Reciever.java)         │                       │   (Sender.java)      │
└──────────┬──────────────┘                       └──────────┬───────────┘
           │                                                 │
           │ Listens on 127.0.0.1:8000                        │ Connects to 127.0.0.1:8000
           │ (serverSocket.accept())                         │ (Socket)
           │                                                 │
           │◄──────────────── TCP Connection ────────────────┤
           │                                                 │
           │◄──────────── JSON Payload (LogEntry) ───────────┤
           │                                                 │
           ▼                                                 ▼
┌─────────────────────────┐                       ┌──────────────────────┐
│ Jackson ObjectMapper    │                       │ Connection Closed    │
│ Deserializes LogEntry   │                       └──────────────────────┘
└──────────┬──────────────┘
           ▼
┌─────────────────────────┐
│ SingleNode              │
│ ├──> WAL (wal.log)      │
│ └──> KVStore            │
└─────────────────────────┘
```

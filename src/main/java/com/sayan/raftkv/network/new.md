# Network Issues: Why Nodes Stopped Sending & Receiving

## Problem 1: Main Thread Crash (Why nodes stop sending)
**Symptom:** A node sends messages to Node A, but fails to connect to Node B, and then stops sending entirely.
**Cause:** In `RaftNode.java`, the `try-catch` block around `new Sender(...)` was removed. When a node tries to connect to an offline peer, it throws a `ConnectException`. This exception bubbles all the way up to `Main.java`, crashing the `while(true)` sending loop. The sending thread dies, but the receiver thread stays alive.
**Fix:** Put the `try-catch` back inside the `for` loop in `RaftNode.SendServer()`.

## Problem 2: Single-Threaded Receiver & Socket Leak (Why Node 3 couldn't connect to Node 1)
**Symptom:** Node 3 got `Connection refused` when connecting to Node 1, even though Node 1's receiver was running.
**Cause:** 
1. In `RaftNode.java`, `sender.close()` was commented out, so the sender never closed the connection.
2. In `Reciever.java`, the server handles one client at a time. It waits in a `while ((json = reader.readLine()) != null)` loop. Because Node 2 never closed its socket, Node 1's receiver got stuck in this loop forever, waiting for more data. It never went back to `accept()` new connections. Thus, when Node 3 tried to connect, Node 1 couldn't accept it.
**Fix:** 
- The sender must close the socket when it's done (`sender.close()`).
- The receiver should spawn a new `Thread` for each connected client, allowing it to handle multiple nodes concurrently.

## Problem 3: Infinite Busy Loop
**Symptom:** Huge amount of messages sent instantly.
**Cause:** The `while(true)` loop in `Main.java` has no delay. It will spam the network as fast as the CPU allows.
**Fix:** Add a `Thread.sleep(3000)` inside the loop to wait before sending again.

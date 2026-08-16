# Java Networking Basics (TCP)

## Overview

A TCP connection has two sides: - **Server (Receiver):** Waits for
clients to connect. - **Client (Sender):** Connects to the server and
sends data.

    Sender (Client) ----TCP Socket----> Receiver (Server)

## Important Classes

### `ServerSocket`

-   Opens a server on a specific port.
-   Waits for incoming client connections.
-   Example:

``` java
ServerSocket server = new ServerSocket(5000);
```

### `Socket`

-   Represents one active TCP connection.
-   Client: `new Socket("localhost", 5000)`
-   Server: Returned by `server.accept()`

### `accept()`

-   Blocks until a client connects.
-   Returns a `Socket` for communicating with that client.

### `getInputStream()`

-   Reads raw bytes sent by the client.

### `InputStreamReader`

-   Converts incoming bytes into Java characters.

### `BufferedReader`

-   Wraps the reader for efficient text reading.
-   `readLine()` reads one complete line.

### `getOutputStream()`

-   Returns the stream used to send data.

### `PrintWriter`

-   Writes text to the socket.
-   `true` enables **autoFlush**, sending data immediately.

### `println()`

-   Sends a line of text and automatically appends a newline (`\n`).

### `readLine()`

-   Reads text until a newline is received.

### `close()`

-   Closes the socket or server socket and releases resources.

## Communication Flow

    Receiver:
    ServerSocket -> accept() -> readLine() -> close()

    Sender:
    Socket -> println() -> close()

## Quick Summary

  Class/Method          Purpose
  --------------------- ---------------------------------
  `ServerSocket`        Listen for connections
  `Socket`              Active client-server connection
  `accept()`            Wait for a client
  `getInputStream()`    Receive bytes
  `getOutputStream()`   Send bytes
  `InputStreamReader`   Bytes → Characters
  `BufferedReader`      Read text efficiently
  `PrintWriter`         Send text easily
  `readLine()`          Read one line
  `println()`           Send one line
  `close()`             Close connection
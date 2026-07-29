# Java TCP Receiver Methods (Quick Notes)

```
Start Program
      │
      ▼
new ServerSocket(port)
      │
      ▼
accept() ────────────────┐
(wait for connection)    │
                         │ Client connects
                         ▼
                  returns Socket
                         │
                         ▼
read()/readLine()
(wait for incoming data)
                         │
                         ▼
write()
(send response)
                         │
                         ▼
close()
(close client connection)
                         │
                         ▼
while(true) → accept() again
```

| Method | Behavior |
|--------|----------|
| `new ServerSocket(port)` | Opens a TCP server on the specified port. |
| `accept()` | **Blocks** until a client connects. Keeps the receiver alive while waiting. |
| `getInputStream()` | Returns the stream used to receive bytes. |
| `read()` / `readLine()` | **Blocks** until data arrives or the client disconnects. |
| `getOutputStream()` | Returns the stream used to send data back to the client. |
| `write()` / `println()` | Sends data to the connected client. |
| `socket.close()` | Closes only the current client connection. |
| `serverSocket.close()` | Stops the server completely; future `accept()` calls fail. |
| `while(true)` | Makes the receiver stay alive forever, continuously accepting new clients. |

**Key Idea**

- `accept()` waits for **connections**.
- `read()` waits for **data**.
- Both are **blocking** methods.
- A TCP receiver remains alive as long as it is inside the `while(true)` loop and the `ServerSocket` is not closed.
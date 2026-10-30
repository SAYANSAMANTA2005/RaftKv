 Previous architechture--> & problem 
 Receiver thread
      │
      ▼
while(true)
      │
      ▼
serverSocket.accept()
      │
      │ waits until a client connects
      ▼
client connects
      │
      ▼
create new Thread
      │
      ├───────────────► Worker Thread
      │                      │
      │                      ▼
      │                 reader.readLine()
      │                      │
      │                      │ waits for messages
      │                      ▼
      │                 process LogEntry
      │
      ▼
go back to accept()
      │
      ▼
wait for next client
 
 

 current architechture -->
 
 
 
                  ┌─────────────────┐
                  │    RaftNode     │
                  │                 │
                  │ state=FOLLOWER  │
                  └───────┬─────────┘
                          │
              ┌───────────┴───────────┐
              │                       │
              ▼                       ▼
       Receiver Thread 
       (seperate Thread)         Election Timer(new thread)
              │                       │
        accept()                      │
              │                       │
        message arrives               │
              │                       │
              ▼                       │
       handleMessage()                │
              │                       │
       valid heartbeat                │
              │                       │
              ▼                       │
      reset election timer ◄──────────┘
                                      │
                                      │ timeout
                                      ▼
                                becomeCandidate()
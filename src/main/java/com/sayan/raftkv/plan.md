Your Project After Week 1
src/main/java/com/sayan/raftkv

│
├── model
│     ├── Command
│     ├── CommandType
│     └── LogEntry
│
├── storage
│     ├── Wal
│     └── KVStore
│
├── node
│     └── SingleNode
│
└── Main


SINGLENODE.JAVA-->
Think of a restaurant

Imagine:

Customer
     │
     ▼
Manager
     │
 ┌───┴────┐
 ▼        ▼
Kitchen  Cashier

The customer doesn't talk directly to the kitchen or cashier.

The manager coordinates everything.

Similarly,

Client
   │
   ▼
SingleNode
   │
 ┌─┴────────────┐
 ▼              ▼
WAL         KVStore


16/08/2026 4:30pm::-->

architechural planning :

current architechutree(before today)
main a raftnode er instace create kora hocche akbar
recevier ta akbar on (start) korbo in main.java
sender ka diye contionuus msg send korbo


current plan(from today): 
[
    Task:
   follower --> candidate[before candidate er life cycle er]
   follower --> ar life cycle er sob task 
   candiadte --> a just transform korano (if electiontimeout hoya jay)
   ,not implementing any features of candidate
]
1) raftnode ka akbar main a intitialise korbo
2) rafnode default nijar state ka FOLLOWER kore rakhbe
3) raftnode tar reciver taka akbar start korlam( akbar start korlei infinited time reciver cholbe )
4) jokhon e kono signal recieve korte parbe (signal of leader ) then --> main a khobor pathabe,je leader exists
  

  if time required to come msg of leader ,exceeds than "electiontimeout" --> then node convert itself into CANDIDATE

  else node remians at follower state & u will set reamining_electiontimeout= electiontimeout

   
   SO FINIAL ARCHITECHTURE(16/08/2024 4:57pm) -->
  in Main.java i will write -->
    1) Raftnode ka akbar intialise korbo(it will start its reciever in background automatically)
    2) Raftnode er Reciever object ka ekta thread a run korabo
    3) Then  we will start a timer in Main.java( this timer will reset to ZERO ,everytime node recives a signal from leader )
    * If timer exceeds electiontimeout then node will convert itself to CANDIDATE 
    [& IN LATER PHASE HERE  WE WILL  start election (send RequestVote RPC to all other nodes),
    BUT FOR now we will just finsh by printing "node:{x} became Candidate"]
  
    


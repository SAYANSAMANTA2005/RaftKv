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
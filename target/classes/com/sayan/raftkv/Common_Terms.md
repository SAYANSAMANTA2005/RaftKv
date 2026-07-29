serialization vs deserialization

1. Serialization

Definition:

Serialization is the process of converting a Java object into a format that can be stored or transmitted.

That format could be:

JSON
XML
Binary
Protocol Buffers
etc.

In your project, you're using JSON.

Example

Suppose you create a Java object:

Command cmd = new Command(
    CommandType.SET,
    "age",
    "21"
);

LogEntry entry = new LogEntry(
    1,
    1,
    cmd
);

Memory looks like:

LogEntry
│
├── index = 1
├── term = 1
└── command
      │
      ├── type = SET
      ├── key = age
      └── value = 21

Now you want to save it in wal.log.

Jackson does:

String json =
mapper.writeValueAsString(entry);

Result:

{
  "index":1,
  "term":1,
  "command":{
      "type":"SET",
      "key":"age",
      "value":"21"
  }
}

This conversion

Java Object
      │
      ▼
JSON String

is called Serialization.

Why serialize?

Because files cannot store Java objects directly.

The operating system understands bytes.

So we convert

Java Object

↓

JSON

↓

Bytes

↓

wal.log
2. Deserialization

Now imagine your program crashes.

When it restarts, it reads:

{
  "index":1,
  "term":1,
  "command":{
      "type":"SET",
      "key":"age",
      "value":"21"
  }
}

But your code needs a LogEntry object, not a JSON string.

Jackson performs:

LogEntry entry =
mapper.readValue(line, LogEntry.class);

It creates:

LogEntry
│
├── index = 1
├── term = 1
└── command
      │
      ├── type = SET
      ├── key = age
      └── value = 21

This conversion

JSON String
      │
      ▼
Java Object

is called Deserialization.

Complete Flow in Your WAL
Writing
LogEntry Object
        │
        ▼
Serialization
        │
        ▼
JSON
        │
        ▼
wal.log
Reading
wal.log
     │
     ▼
JSON
     │
     ▼
Deserialization
     │
     ▼
LogEntry Object
Jackson Methods
Serialization
String json =
mapper.writeValueAsString(entry);

Meaning

Object → JSON
Deserialization
LogEntry entry =
mapper.readValue(json, LogEntry.class);

Meaning

JSON → Object
Real-world Examples

Serialization is used when:

Saving objects to a file (your WAL)
Sending data over HTTP APIs
Sending messages through Kafka
Writing to Redis
Saving user sessions
Writing checkpoints

Deserialization is used when:

Reading files
Receiving API requests
Reading Kafka messages
Loading cached objects
Recovering database state after a restart
Easy way to remember
Serialization
--------------------
Object
   │
   ▼
Store / Send


Deserialization
--------------------
Stored Data
     │
     ▼
Object

Or, in one line:

Serialization = Object → JSON (or another storable/transmittable format).
Deserialization = JSON (or another stored/transmitted format) → Object.
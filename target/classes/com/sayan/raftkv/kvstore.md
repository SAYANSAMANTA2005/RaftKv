What is KVStore?

A Key-Value Store is simply

Key

↓

Value

Examples

age → 21

name → Sayan

city → Kolkata

In Java,

HashMap<String,String>

is already a key-value store.

So our class is mostly a wrapper around HashMap.

Responsibilities

Only three things.

SET

↓

map.put()

----------------

DELETE

↓

map.remove()

----------------

GET

↓

map.get()

Nothing else.

Internal Structure
KVStore

│

└── HashMap<String,String>

That's it.

apply(Command)

This is the important method.

Instead of

set()
delete()

we use

apply(Command)

because Raft logs commands, not individual function calls.

Suppose

Command

type = SET

key = age

value = 21

apply() should understand

SET

↓

put(age,21)

Another example

Command

type = DELETE

key = age

↓

remove(age)
Workflow

Client

↓

Creates

SET age 21

↓

becomes

Command

↓

stored in WAL

↓

passed to

kv.apply(command)

↓

HashMap updates

get()

Later

kv.get("age")

↓

returns

21

No WAL involved.

size()

Just

How many keys currently exist?

Suppose

age

name

city

↓

size = 3
Fixed! In GlobalVar.java, msgReceived is now static.

Why static is the right choice here:



Instance field (before)

Static field (after ✅)

Belongs to

A specific GlobalVar object

The GlobalVar class itself

Access

new GlobalVar().msgReceived

GlobalVar.msgReceived

Use case

Per-object state

Shared global state

Since GlobalVar is intended as a container for shared global state (hence the name), static is exactly what you want — there's one shared value visible across the entire application.
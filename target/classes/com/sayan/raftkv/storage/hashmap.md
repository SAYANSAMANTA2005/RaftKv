Do you need to check first?

Many beginners write:

if (store.containsKey(command.getKey())) {
    store.remove(command.getKey());
}

This is unnecessary because remove() already handles missing keys safely.

Just write:

store.remove(command.getKey());

It's shorter and avoids an extra lookup.
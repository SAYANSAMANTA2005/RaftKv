package com.sayan.raftkv.storage;

import java.util.HashMap;
import java.util.Map;

import com.sayan.raftkv.Command;
import com.sayan.raftkv.CommandType;

public class KVStore {
    private Map<String, String> store;

    public KVStore() {
        store = new HashMap<>();
    }

    public void apply(Command command) {
        // Check command type:
        // - SET -> store.put(key, value)
        // - DELETE -> store.remove(key)
        switch (command.getType()) {
            case SET:
                store.put(command.getKey(), command.getValue());
                break;
            /*
             * Current Problem with DELETE -->
             * once a key's value is CHanged mutiple times
             * 
             * THen Ideally after deleting the key,is not much useful operation
             * 
             * U need if the last operation on the key is removed
             * --> then the state of value of that particular key
             */
            case DELETE:
                store.remove(command.getKey());
                break;
            default:
                throw new IllegalArgumentException(
                        "UnKnown  Command Type :" + command.getType());

        }
    }

    public String get(String key) {
        // Return the value associated with the key
        return store.get(key);
    }

    public int size() {
        // Return the number of key-value pairs
        return store.size();
    }
}

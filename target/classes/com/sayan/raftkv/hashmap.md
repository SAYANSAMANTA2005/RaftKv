# Java HashMap Cheat Sheet

## Import
```java
import java.util.HashMap;
```

## Create
```java
HashMap<String, Integer> map = new HashMap<>();
```

## Insert / Update
```java
map.put("Alice", 90);
map.put("Bob", 85);
map.put("Alice", 95);   // Updates existing value
```

## Get Value
```java
int marks = map.get("Alice");
```

## Remove Entry
```java
map.remove("Bob");
```

## Check Key / Value
```java
map.containsKey("Alice");
map.containsValue(95);
```

## Size & Empty
```java
map.size();
map.isEmpty();
```

## Clear All Entries
```java
map.clear();
```

## Iterate (Recommended)
```java
for (HashMap.Entry<String, Integer> entry : map.entrySet()) {
    System.out.println(entry.getKey() + " -> " + entry.getValue());
}
```

## Iterate Keys
```java
for (String key : map.keySet()) {
    System.out.println(key);
}
```

## Iterate Values
```java
for (Integer value : map.values()) {
    System.out.println(value);
}
```

## Useful Methods
- `put(key, value)` → Insert/Update
- `get(key)` → Retrieve value
- `remove(key)` → Delete entry
- `containsKey(key)` → Check key exists
- `containsValue(value)` → Check value exists
- `size()` → Number of entries
- `isEmpty()` → Check if map is empty
- `clear()` → Remove all entries
- `keySet()` → All keys
- `values()` → All values
- `entrySet()` → All key-value pairs
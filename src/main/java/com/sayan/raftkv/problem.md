# Jackson Deserialization Error — Explained

## The Problem

Jackson's `ObjectMapper` deserializes JSON in **two steps**:

1. **Instantiate** the class (needs a no-arg constructor)
2. **Populate** fields (uses setters or field injection)

`LogEntry` only had a parameterized constructor:

```java
public LogEntry(long index, long term, Command command) { ... }
```

In Java, once you define **any** constructor explicitly, the compiler **stops** generating
the default no-arg constructor. Jackson couldn't create an empty instance, so it threw:

```
InvalidDefinitionException: Cannot construct instance of `LogEntry`
(no Creators, like default constructor, exist)
```

## The Fix

Add a no-arg constructor so Jackson can instantiate the class first:

```java
public LogEntry() {
    // required by Jackson for deserialization
}
```

Since `LogEntry` already had **setters** for all fields, Jackson could then populate
`term`, `index`, and `command` automatically after construction.

## Why `Command` Didn't Fail

`Command` already had an explicit no-arg constructor — so Jackson had no trouble there.

## Alternatives (if you don't want a no-arg constructor)

| Option | How |
|---|---|
| `@JsonCreator` on existing constructor | Annotate each param with `@JsonProperty` |
| Make it a Java `record` | Records are Jackson-friendly by default (Jackson 2.12+) |

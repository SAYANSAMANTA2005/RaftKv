package com.sayan.raftkv.models;

public class LogEntry {
  private long term;
  private long index;
  private Command command;

  public LogEntry() {
    // default constructor required by Jackson for deserialization
  }

  public LogEntry(long index, long term, Command command) {
    this.term = term;
    this.index = index;
    this.command = command;
  }

  // getters
  public long getTerm() {
    return term;
  }

  public long getIndex() {
    return index;
  }

  public Command getCommand() {
    return command;
  }

  // setters
  public void setTerm(long term) {
    this.term = term;
  }

  public void setIndex(long index) {
    this.index = index;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

}

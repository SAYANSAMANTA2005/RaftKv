package com.sayan.raftkv;

public class Command {
    private CommandType type;
    private String key;
    private String value;

    public Command() {
        // default constructor
    }

    public Command(CommandType type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }
    //getters
    public CommandType getType(){
        return type;
    }
    public String getKey(){
        return key;
    }
    public String getValue(){
        return value;
    }
    //setters
    public void setType(CommandType type){
        this.type=type;
    }
    public void setKey(String key){
        this.key=key;
    }
    public void setValue(String value){
        this.value=value;
    }
}
    


package com.example.uts.model;

public class Message {
    private CharSequence text;
    private long timestamp;
    private CharSequence sender;

    public Message(CharSequence text, CharSequence sender) {
        this.text = text;
        timestamp = System.currentTimeMillis();
        this.sender = sender;
    }

    public CharSequence getText() {
        return text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public CharSequence getSender() {
        return sender;
    }
}

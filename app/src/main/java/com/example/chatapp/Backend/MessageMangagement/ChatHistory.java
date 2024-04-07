package com.example.chatapp.Backend.MessageMangagement;

import java.security.Key;
import java.util.UUID;

import java.util.ArrayList;
import java.util.List;


public class ChatHistory {
    private UUID sender;
    private UUID receiver;
    private Key key; // Placeholder attribute
    private List<ChatMessage> messages;

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void setReceiver(UUID receiver) {
        this.receiver = receiver;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public ChatHistory(UUID sender, UUID receiver, Key key) {
        this.sender = sender;
        this.receiver = receiver;
        this.key = key;
        this.messages = new ArrayList<>();
    }


}
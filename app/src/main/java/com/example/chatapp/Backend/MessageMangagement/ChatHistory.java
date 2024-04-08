package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;


public class ChatHistory {
    private UUID sender;
    private UUID receiver;
    private SecretKey key;
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

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public ChatHistory(UUID sender, UUID receiver, SecretKey key) {
        this.sender = sender;
        this.receiver = receiver;
        this.key = key;
        this.messages = new ArrayList<>();
    }


}
package com.example.chatapp.Backend.MessageMangagement;

import java.util.Date;

import java.util.UUID;

public class ChatMessage {
    private UUID messageId;
    private String message;
    private Date date;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Constructors, getters, and setters
    public ChatMessage(UUID messageId, String message) {
        this.messageId = messageId;
        this.message = message;
        this.date = new Date(); // The date the message was created
    }
}

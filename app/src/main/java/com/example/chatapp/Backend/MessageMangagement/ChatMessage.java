package com.example.chatapp.Backend.MessageMangagement;

import java.io.Serializable;
import java.util.Date;

import java.util.UUID;

public class ChatMessage implements Serializable {
    private UUID messageId;
    private UUID senderId;
    private byte[] message;
    private Date date;

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public byte[] getMessage() {
        return message;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Constructors, getters, and setters
    public ChatMessage(UUID messageId, UUID senderId, byte[] message) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.message = message;
        this.date = new Date(); // The date the message was created
    }
}

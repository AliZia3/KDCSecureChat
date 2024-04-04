package com.example.chatapp.Backend.MessageMangagement;

import java.util.Date;
import java.util.UUID;

public class ChatHistory {

    UUID sender;
    UUID receiver;
    String message;
    Date date;
    public ChatHistory(UUID sender, UUID receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.date = new Date();
    }
}

package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;

abstract class MessageManagement {
    SendTextMessage sendTextMessage;
    AccessChatHistory accessChatHistory;
    StartChat startChat;
    abstract void sendTextMessage(UUID uid);
    abstract ChatHistory retrieveChatHistory(UUID sender, UUID receiver);
    abstract void startChat(UUID uid);

}

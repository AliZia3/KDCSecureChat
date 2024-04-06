package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;

public class MessageManager extends MessageManagement {
    public MessageManager() {
        sendTextMessage = new SendTextMessage();
        accessChatHistory = new AccessChatHistory();
        startChat = new StartChat();
    }
    void sendTextMessage(UUID uid) {
        sendTextMessage.sendTextMessage(uid);
    }

    ChatHistory retrieveChatHistory(UUID sender, UUID receiver) {
        return accessChatHistory.accessChatHistory(sender, receiver);
    }

    void startChat(UUID uid) {
        startChat.startChat(uid);
    }
}

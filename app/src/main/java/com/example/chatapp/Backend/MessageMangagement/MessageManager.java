package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;

public class MessageManager extends MessageManagement {
    public MessageManager() {
        sendTextMessage = new SendTextMessage();
        accessChatHistory = new AccessChatHistory();
        startChat = new StartChat();
    }
    void sendTextMessage(UUID senderId, UUID receiverId, String message) {
        sendTextMessage.sendTextMessage(senderId, receiverId, message);
    }

    ChatHistory retrieveChatHistory(UUID sender, UUID receiver, final AccessChatHistory.ChatHistoryListener listener) {
        return accessChatHistory.accessChatHistory(sender, receiver, listener);
    }

    void startChat(UUID uid) {
        startChat.startChat(uid);
    }
}

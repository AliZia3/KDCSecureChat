package com.example.chatapp.Backend.MessageMangagement;

import java.security.Key;
import java.util.UUID;

abstract class MessageManagement {
    SendTextMessage sendTextMessage;
    AccessChatHistory accessChatHistory;
    StartChat startChat;
    abstract void sendTextMessage(UUID senderId, UUID receiverId, String message);
    abstract ChatHistory retrieveChatHistory(UUID sender, UUID receiver, final AccessChatHistory.ChatHistoryListener listener);
    abstract boolean startChat(UUID senderId, UUID receiverId);

}

package com.example.chatapp.Backend.MessageMangagement;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MessageManager extends MessageManagement {
    public MessageManager() {
        sendTextMessage = new SendTextMessage();
        accessChatHistory = new AccessChatHistory();
        startChat = new StartChat();
    }
    void sendTextMessage(UUID senderId, UUID receiverId, String message) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        sendTextMessage.sendTextMessage(senderId, receiverId, message);
    }

    ChatHistory retrieveChatHistory(UUID sender, UUID receiver, final AccessChatHistory.ChatHistoryListener listener) {
        return accessChatHistory.accessChatHistory(sender, receiver, listener);
    }

    boolean startChat(UUID senderId, UUID receiverId) {
        return startChat.startChat(senderId, receiverId);
    }
}

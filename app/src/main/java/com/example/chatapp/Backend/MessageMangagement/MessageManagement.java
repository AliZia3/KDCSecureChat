package com.example.chatapp.Backend.MessageMangagement;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

abstract class MessageManagement {
    SendTextMessage sendTextMessage;
    AccessChatHistory accessChatHistory;
    StartChat startChat;
    abstract void sendTextMessage(UUID senderId, UUID receiverId, String message) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    abstract ChatHistory retrieveChatHistory(UUID sender, UUID receiver, final AccessChatHistory.ChatHistoryListener listener);
    abstract boolean startChat(UUID senderId, UUID receiverId);

}

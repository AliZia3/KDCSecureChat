package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;
//import SymmetricKey
//import AccountManagement
//import NotificationManagement

abstract class MessageManagement {
    abstract void sendTextMessage(UUID uid);
    abstract ChatHistory retrieveChatHistory(UUID sender, UUID receiver);
    abstract void startChat(UUID uid);

}

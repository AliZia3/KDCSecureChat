package com.example.chatapp.Backend.MessageMangagement;

import java.util.UUID;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendTextMessage {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    public void sendTextMessage(UUID senderId, UUID receiverId, String message) {
        // Generate unique identifiers
        String chatId = senderId.toString() + "-" + receiverId.toString(); // Simple way to generate a chat ID
        UUID messageId = UUID.randomUUID();

        // Create a new ChatMessage object
        ChatMessage chatMessage = new ChatMessage(messageId, message);

        // Push this message to the correct chat in Firebase
        chatsRef.child(chatId).child("messages").push().setValue(chatMessage);

        //TODO: update chat metadata (sender, receiver, etc.) here as well (maybe)
    }
}
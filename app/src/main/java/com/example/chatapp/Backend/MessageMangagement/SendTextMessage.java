package com.example.chatapp.Backend.MessageMangagement;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SendTextMessage {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    public void sendTextMessage(UUID senderId, UUID receiverId, String message) {

        UUID messageId = UUID.randomUUID();

        // Simple way to generate a chat ID
        final String[] chatId = {senderId.toString() + ":" + receiverId.toString()};

        // Check to see if a chat already exists between two users
        String reversedChatId = receiverId + ":" + senderId;

        chatsRef.child(reversedChatId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // If the chat already exists, set chatId to the existing one
                if (snapshot.exists()) {
                    chatId[0] = reversedChatId;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Cancelled logic }
            }
        });


        // Create a new ChatMessage object
        ChatMessage chatMessage = new ChatMessage(messageId, senderId, message);

        // Serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(chatMessage);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String serializedChatMessage = bos.toString();

        // Push this message to the correct chat in Firebase
        chatsRef.child(chatId[0]).child("messages").push().setValue(serializedChatMessage);

        // Push senderId to its respective message
        chatsRef.child(chatId[0]).child("messages").child(serializedChatMessage).push().setValue(senderId);
        //TODO: update chat metadata (sender, receiver, etc.) here as well (maybe)
    }
}
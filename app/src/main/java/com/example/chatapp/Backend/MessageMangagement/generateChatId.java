package com.example.chatapp.Backend.MessageMangagement;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class generateChatId {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    // Either generates a new chatId, or finds one that already exists between the two users
    public String[] generate(UUID senderId, UUID receiverId) {
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
        return chatId;
    }
}

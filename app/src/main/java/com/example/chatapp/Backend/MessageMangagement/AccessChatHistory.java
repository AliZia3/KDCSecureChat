package com.example.chatapp.Backend.MessageMangagement;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccessChatHistory {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    public interface ChatHistoryListener {
        void onChatHistoryReceived(List<ChatMessage> messages);
        void onError(String error);
    }

    public ChatHistory accessChatHistory(UUID senderId, UUID receiverId, final ChatHistoryListener listener) {
        String chatId = senderId.toString() + "-" + receiverId.toString(); // Simple chat ID generation logic

        chatsRef.child(chatId).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ChatMessage> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                    messages.add(message);
                }
                listener.onChatHistoryReceived(messages); // Notify listener with the list of messages
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Failed to read chat messages: " + databaseError.getMessage()); // Notify listener of error
            }
        });
        return null;
    }
}

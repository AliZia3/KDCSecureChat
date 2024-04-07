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

    // Returns all data regarding chats and messages
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


    // Simplified getter which only returns senderId and the message
    public interface MessagesListener {
        void onAccessMessagesReceived(List<SimpleChatMessage> simpleMessages);
        void onError(String error);
    }

    public void accessMessages(UUID senderId, UUID receiverId, final MessagesListener listener) {
        String chatId = senderId.toString() + "-" + receiverId.toString(); // Simple chat ID generation logic

        chatsRef.child(chatId).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SimpleChatMessage> simpleMessages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                    if (message != null) {
                        SimpleChatMessage simpleMessage = new SimpleChatMessage(message.getSenderId().toString(), message.getMessage());
                        simpleMessages.add(simpleMessage);
                    }
                }
                listener.onAccessMessagesReceived(simpleMessages); // Notify listener
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Failed to read chat messages: " + databaseError.getMessage()); // Notify listener of error
            }
        });
    }


    // Most simplified getter that only returns a list of all messages, regardless of sender
    public interface RawMessagesListener {
        void onRawMessagesReceived(List<String> messages);
        void onError(String error);
    }

    // New simplified method to get just the messages as a list of strings
    public void getRawMessages(UUID senderId, UUID receiverId, final RawMessagesListener listener) {
        String chatId = senderId.toString() + "-" + receiverId.toString(); // Simple chat ID generation logic

        chatsRef.child(chatId).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    // Extracting the message field directly from each message
                    String message = messageSnapshot.child("message").getValue(String.class);
                    if (message != null) {
                        messages.add(message);
                    }
                }
                listener.onRawMessagesReceived(messages); // Pass the list of messages to the listener
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Failed to read chat messages: " + databaseError.getMessage()); // Notify listener of error
            }
        });
    }
}


class SimpleChatMessage {
    private String senderId;
    private String message;

    public SimpleChatMessage(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    // Getters
    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }
}


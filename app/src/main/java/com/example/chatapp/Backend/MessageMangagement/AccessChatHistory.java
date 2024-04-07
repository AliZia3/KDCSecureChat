package com.example.chatapp.Backend.MessageMangagement;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.chatapp.Backend.SymmetricKey.SymmetricKeyCryptoSystemManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AccessChatHistory {
    DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    public interface ChatHistoryListener {
        void onChatHistoryReceived(List<ChatMessage> messages);
        void onError(String error);
    }

    // Returns all data regarding chats and messages
    public ChatHistory accessChatHistory(UUID senderId, UUID receiverId, final ChatHistoryListener listener) {
        generateChatId generate = new generateChatId();

        String[] chatId = generate.generate(senderId, receiverId);

        chatsRef.child(chatId[0]).child("messages").addValueEventListener(new ValueEventListener() {
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
        generateChatId generate = new generateChatId();

        String[] chatId = generate.generate(senderId, receiverId);

        chatsRef.child(chatId[0]).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
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
        generateChatId generate = new generateChatId();

        String[] chatId = generate.generate(senderId, receiverId);

        final String[] serializedKey = new String[1];

        DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");


// Asynchronously retrieve the data once.
        Task<DataSnapshot> task = chatsRef.child(chatId[0]).child("key").get();

        task.addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    DataSnapshot dataSnapshot = task.getResult();
                    // Check if the key exists
                    if (dataSnapshot.exists()) {
                        // Assuming the key is stored as a String
                        String keyValue = dataSnapshot.child("key").getValue(String.class);
                        Log.d("asdfasdf","Retrieved key: " + keyValue);
                    } else {
                        Log.d(";lkj;lkj","Key not found.");
                    }
                } else {
                    Log.d("zxcv","Error getting data: " + task.getException().getMessage());
                }
            }
        });



        // Assume serializedKey is your serialized key in String format
        assert serializedKey[0] != null;
        byte[] bytes = serializedKey[0].getBytes();

        // Deserialize
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Key deserializedKey = null;
        try {
            ObjectInputStream in = new ObjectInputStream(bis);
            deserializedKey = (Key) in.readObject();

            // Use deserializedKey as needed
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Key finalDeserializedKey = deserializedKey;
        chatsRef.child(chatId[0]).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    // Extracting the message field directly from each message
                    byte[] message = {messageSnapshot.child("message").getValue(byte.class)};

                    SymmetricKeyCryptoSystemManager decrypt = new SymmetricKeyCryptoSystemManager();

                    try {
                        messages.add(decrypt.decrypt(message, finalDeserializedKey));
                    } catch (InvalidAlgorithmParameterException | InvalidKeyException |
                             BadPaddingException | NoSuchAlgorithmException |
                             IllegalBlockSizeException | NoSuchPaddingException e) {
                        throw new RuntimeException(e);
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
    private byte[] message;

    public SimpleChatMessage(String senderId, byte[] message) {
        this.senderId = senderId;
        this.message = message;
    }

    // Getters
    public String getSenderId() {
        return senderId;
    }

    public byte[] getMessage() {
        return message;
    }
}


package com.example.chatapp.Backend.MessageMangagement;

import com.example.chatapp.Backend.SymmetricKey.SymmetricKeyCryptoSystemManager;
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
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class AccessChatHistory {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

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

        // First, fetch and decrypt the key
        fetchAndDecryptMessages(chatId, new SecretKeyCallback() {
            @Override
            public void onSuccess(SecretKey key) {
                // Key is successfully fetched and decrypted, now fetch messages
                chatsRef.child(chatId[0]).child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> messages = new ArrayList<>();
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            String encodedMessage = messageSnapshot.child("message").getValue(String.class);
                            if (encodedMessage != null) {
                                byte[] decodedBytes = new byte[0];
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    decodedBytes = Base64.getDecoder().decode(encodedMessage);
                                }
                                SymmetricKeyCryptoSystemManager decrypt = new SymmetricKeyCryptoSystemManager();
                                try {
                                    messages.add(decrypt.decrypt(decodedBytes, key));
                                } catch (InvalidAlgorithmParameterException | InvalidKeyException |
                                         BadPaddingException | NoSuchAlgorithmException |
                                         IllegalBlockSizeException | NoSuchPaddingException e) {
                                    e.printStackTrace();
                                    listener.onError("Failed to decrypt message: " + e.getMessage());
                                    return;
                                }
                            }
                        }
                        listener.onRawMessagesReceived(messages);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onError("Failed to read chat messages: " + databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    public interface SecretKeyCallback {
        void onSuccess(SecretKey key);
        void onError(String error);
    }

    public void fetchAndDecryptMessages(String[] chatId, final SecretKeyCallback callback) {
        chatsRef.child(chatId[0]).child("key").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String serializedKey = dataSnapshot.getValue(String.class);
                if (serializedKey != null) {
                    try {
                        byte[] bytes = new byte[0];
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            bytes = Base64.getDecoder().decode(serializedKey);
                        }
                        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                        ObjectInputStream in = new ObjectInputStream(bis);
                        SecretKey deserializedKey = (SecretKey) in.readObject();
                        callback.onSuccess(deserializedKey);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        callback.onError("Failed to deserialize key: " + e.getMessage());
                    }
                } else {
                    callback.onError("Serialized key is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError("Failed to read chat messages: " + databaseError.getMessage());
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







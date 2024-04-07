package com.example.chatapp.Backend.MessageMangagement;

import androidx.annotation.NonNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.example.chatapp.Backend.SymmetricKey.Encrypt;
import com.example.chatapp.Backend.SymmetricKey.SymmetricKeyCryptoSystemManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SendTextMessage {
    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    public void sendTextMessage(UUID senderId, UUID receiverId, String message) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        UUID messageId = UUID.randomUUID();

        generateChatId generate = new generateChatId();

        AccessChatHistory accessChatHistory = new AccessChatHistory();

        String[] chatId = generate.generate(senderId, receiverId);


        SymmetricKeyCryptoSystemManager encrypt = new SymmetricKeyCryptoSystemManager();

        // Create a new ChatMessage object
        ChatMessage chatMessage = new ChatMessage(messageId, senderId, encrypt.encrypt(message, accessChatHistory.fetchAndDecryptMessages(chatId)));

        // Serialization for ChatMessage object
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
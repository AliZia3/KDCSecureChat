package com.example.chatapp.Backend.MessageMangagement;

import androidx.annotation.NonNull;

import com.example.chatapp.Backend.KDC.KDCManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

import javax.crypto.SecretKey;

public class StartChat {

    private final DatabaseReference chatsRef = FirebaseDatabase.getInstance().getReference("Chats");

    // startChat called when user wants to start a new chat
    public boolean startChat(UUID senderId, UUID receiverId) {
        String chatId = senderId.toString() + "-" + receiverId.toString(); // Simple chat ID generation logic

        DatabaseReference chatRef = chatsRef.child(chatId);
        chatRef.child("senderId").setValue(senderId.toString());
        chatRef.child("receiverId").setValue(receiverId.toString());

        // Calling createKey to generate a new key for this chat
        KDCManager kdc = new KDCManager();
        SecretKey keyGenerated = kdc.createKey();

        // Serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(keyGenerated);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String serializedKey = bos.toString();


        // Pushes key to database
        final boolean[] stored = {false};
        chatRef.child("key").setValue(serializedKey).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    stored[0] = true;
                    System.out.println("Key stored successfully"); //TODO: temp
                }
            }
        });

        return stored[0];
    }

}

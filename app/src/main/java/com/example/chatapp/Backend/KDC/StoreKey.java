package com.example.chatapp.Backend.KDC;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.util.Arrays;
import java.util.UUID;

public class StoreKey {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public boolean storeKey(Key key) {
        // AES Keys are unique, so we can use the key itself as the ID after serializing it

        // Serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(key);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String serializedKey = bos.toString();


        // Adding to db
        DatabaseReference reference = database.getReference().child("KDC");

        final boolean[] stored = {false};
        reference.setValue(serializedKey).addOnCompleteListener(new OnCompleteListener<Void>() {
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

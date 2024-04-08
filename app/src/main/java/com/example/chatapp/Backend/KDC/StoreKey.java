package com.example.chatapp.Backend.KDC;

import android.os.Build;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.crypto.SecretKey;

public class StoreKey {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public boolean storeKey(SecretKey key) {
        // AES Keys are unique, so we can use the key itself as the ID after serializing it

        // Serialization
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String serializedKey = null;

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(key);
            out.flush();
            // Properly encode the byte array to a Base64 string
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serializedKey = Base64.getEncoder().encodeToString(bos.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



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

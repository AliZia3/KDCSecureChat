package com.example.chatapp.Backend.KDC;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.security.Key;

import javax.crypto.SecretKey;

public class DeleteKey {

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public boolean deleteKey(SecretKey key) {
        // Keys are stored in serialized form, so we need to serialize the key to delete it
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(key);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String serializedKey = bos.toString();


        // Querying the database to find the key
        DatabaseReference reference = database.getReference().child("KDC");

        Query query = reference.orderByValue().equalTo(serializedKey);

        // Deleting the key
        final boolean[] deleted = {false};

        query.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    deleted[0] = true;
                    System.out.println("Key stored successfully"); //TODO: temp
                }
            }
        });

        return deleted[0];
    }
}

package com.example.chatapp.Backend.KDC;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateKeys {
    public void updateKeys() {
        // TODO: Implement key update logic
        /* This will update every key in the KDC simultaneously with a new set of keys.
         * This is done to ensure that the keys are not compromised.
         * This method will be called periodically to update the keys.
         *
         * Ran out of time to implement this unfortunately, so for now keys are static. We would have used
         * the subscribe notify pattern to allow chats to subscribe to the KDC and be notified when the keys update
         * to get the new keys.
         */
    }
}

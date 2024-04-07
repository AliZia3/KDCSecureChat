package com.example.chatapp.Backend.KDC;

import java.security.Key;
import java.util.UUID;

public class KDCManager extends KDCManagement {
    public KDCManager() {
        generateKey = new CreateKeys();
        keyDeleter = new DeleteKey();
        keyStorer = new StoreKey();
    }

    public Key createKey() { // TODO Called when a chat starts
        Key key = generateKey.generateKey();
        storeKey(key);
        return key;
    }

    boolean deleteKey(Key key) { // TODO Called when a chat ends
        return keyDeleter.deleteKey(key);
    }

    boolean storeKey(Key key) {
        return keyStorer.storeKey(key);
    }
}

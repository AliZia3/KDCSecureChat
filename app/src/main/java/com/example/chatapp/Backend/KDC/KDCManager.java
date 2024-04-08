package com.example.chatapp.Backend.KDC;


import javax.crypto.SecretKey;

public class KDCManager extends KDCManagement {
    public KDCManager() {
        generateKey = new CreateKeys();
        keyDeleter = new DeleteKey();
        keyStorer = new StoreKey();
    }

    public SecretKey createKey() { // TODO Called when a chat starts
        SecretKey key = generateKey.generateKey();
        storeKey(key);
        return key;
    }

    boolean deleteKey(SecretKey key) { // TODO Called when a chat ends
        return keyDeleter.deleteKey(key);
    }

    boolean storeKey(SecretKey key) {
        return keyStorer.storeKey(key);
    }
}

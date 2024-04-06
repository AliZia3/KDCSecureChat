package com.example.chatapp.Backend.KDC;

import java.security.Key;
import java.util.UUID;

public class KDCManager extends KDCManagement {
    public KDCManager() {
        generateKey = new CreateKeys();
        keyDeleter = new DeleteKey();
        keyDistributor = new DistributeKeys();
    }

    Key createKey() {
        return generateKey.generateKey();
    }

    boolean deleteKey(Key key) {
        return keyDeleter.deleteKey(key);
    }

    boolean distributeKeys(UUID[] targets, Key key) {
        return keyDistributor.distributeKey(targets,key);
    }
}

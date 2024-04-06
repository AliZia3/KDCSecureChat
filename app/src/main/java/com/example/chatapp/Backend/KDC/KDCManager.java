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
        return null;
    }

    boolean deleteKey(Key key) {
        return false;
    }

    boolean distributeKeys(UUID[] targets, Key key) {
        return false;
    }
}

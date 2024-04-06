package com.example.chatapp.Backend.KDC;

import java.security.Key;
import java.util.UUID;

abstract class KDCManagement {
    CreateKeys generateKey;
    DeleteKey keyDeleter;
    DistributeKeys keyDistributor;

    abstract Key createKey();
    abstract boolean deleteKey(Key key);
    abstract boolean distributeKeys(UUID[] targets, Key key);
}

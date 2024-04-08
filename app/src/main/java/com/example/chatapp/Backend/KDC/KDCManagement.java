package com.example.chatapp.Backend.KDC;

import java.security.Key;
import java.util.UUID;

import javax.crypto.SecretKey;

abstract class KDCManagement {
    CreateKeys generateKey;
    DeleteKey keyDeleter;
    StoreKey keyStorer;

    abstract SecretKey createKey();
    abstract boolean deleteKey(SecretKey key);
    abstract boolean storeKey(SecretKey key);
}

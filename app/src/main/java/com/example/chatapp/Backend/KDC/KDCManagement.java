package com.example.chatapp.Backend.KDC;


import javax.crypto.SecretKey;

abstract class KDCManagement {
    CreateKeys generateKey;
    DeleteKey keyDeleter;
    StoreKey keyStorer;

    UpdateKeys keyUpdater;

    abstract SecretKey createKey();
    abstract boolean deleteKey(SecretKey key);
    abstract boolean storeKey(SecretKey key);

    abstract void updateKeys();
}

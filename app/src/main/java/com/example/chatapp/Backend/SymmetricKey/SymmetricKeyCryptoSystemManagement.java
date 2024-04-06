package com.example.chatapp.Backend.SymmetricKey;

import java.security.Key;

abstract class SymmetricKeyCryptoSystemManagement {
    Decrypt decrypt;
    Encrypt encrypt;
    VerifyKey verifyKey;
    abstract Boolean verifyKey(Key key);
    abstract String encrypt(Key key);
    abstract String decrypt(Key key);

}

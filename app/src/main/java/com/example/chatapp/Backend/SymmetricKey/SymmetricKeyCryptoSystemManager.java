package com.example.chatapp.Backend.SymmetricKey;

import java.security.Key;

public class SymmetricKeyCryptoSystemManager extends SymmetricKeyCryptoSystemManagement{
    public SymmetricKeyCryptoSystemManager() {
        encrypt = new Encrypt();
        decrypt = new Decrypt();
        verifyKey = new VerifyKey();
    }

    Boolean verifyKey(Key key) {
        return verifyKey.verifyKey(key);
    }

    String encrypt(Key key) {
        return encrypt.encrypt(key);
    }

    String decrypt(Key key) {
        return decrypt.decrypt(key);
    }
}

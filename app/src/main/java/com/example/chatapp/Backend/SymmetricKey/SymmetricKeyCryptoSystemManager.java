package com.example.chatapp.Backend.SymmetricKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class SymmetricKeyCryptoSystemManager extends SymmetricKeyCryptoSystemManagement{
    public SymmetricKeyCryptoSystemManager() {
        encrypt = new Encrypt();
        decrypt = new Decrypt();
        IVGen = new GenerateIV();
        iv = IVGen.generateIV();
    }

    byte[] encrypt(String message, Key key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encrypt.encrypt(message, key, iv);
    }

    String decrypt(byte[] encryptedMessage, Key key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return decrypt.decrypt(encryptedMessage, key, iv);
    }
}

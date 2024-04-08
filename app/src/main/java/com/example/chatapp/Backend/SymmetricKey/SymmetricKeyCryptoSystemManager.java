package com.example.chatapp.Backend.SymmetricKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class SymmetricKeyCryptoSystemManager extends SymmetricKeyCryptoSystemManagement{
    public SymmetricKeyCryptoSystemManager() {
        encrypt = new Encrypt();
        decrypt = new Decrypt();
        IVGen = new GenerateIV();
        iv = IVGen.generateIV();
    }

    public byte[] encrypt(String message, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return encrypt.encrypt(message, key, iv);
    }

    public String decrypt(byte[] encryptedMessage, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return decrypt.decrypt(encryptedMessage, key, iv);
    }
}

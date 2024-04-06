package com.example.chatapp.Backend.SymmetricKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;

abstract class SymmetricKeyCryptoSystemManagement {
    Decrypt decrypt;
    Encrypt encrypt;
    GenerateIV IVGen;
    abstract IvParameterSpec generateIV();
    abstract byte[] encrypt(String message, Key key, IvParameterSpec iv) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    abstract String decrypt(byte[] encryptedMessage, Key key, IvParameterSpec iv) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

}

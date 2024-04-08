package com.example.chatapp.Backend.SymmetricKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

// Abstracted class to manage Symmetric Key Crypto System
// Does this using the encrypt, decrypt and GenerateIV classes
abstract class SymmetricKeyCryptoSystemManagement {
    Decrypt decrypt;
    Encrypt encrypt;
    GenerateIV IVGen;
    IvParameterSpec iv;
    abstract byte[] encrypt(String message, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
    abstract String decrypt(byte[] encryptedMessage, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

}

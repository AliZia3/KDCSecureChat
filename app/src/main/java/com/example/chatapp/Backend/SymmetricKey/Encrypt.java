package com.example.chatapp.Backend.SymmetricKey;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Encrypt {
    // This method encrypts a plaintext message using AES encryption in CBC mode with PKCS5 padding.
    // It takes the plaintext message, a cryptographic key, and an initialization vector as input parameters.
    public byte[] encrypt(String message, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Initialize a Cipher object with the AES/CBC/PKCS5Padding transformation
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Initialize the cipher for encryption mode with the provided key and initialization vector
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        // Encrypt the plaintext message by converting it to a byte array and using the cipher's doFinal method
        return cipher.doFinal(message.getBytes());
    }
}

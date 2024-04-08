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

public class Decrypt {
    // This method decrypts a byte array representing an encrypted message using AES decryption in CBC mode with PKCS5 padding.
    // It takes the encrypted message, a cryptographic key, and an initialization vector as input parameters.
    public String decrypt(byte[] encryptedMessage, SecretKey key, IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        // Initialize a Cipher object with the AES/CBC/PKCS5Padding transformation
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Initialize the cipher for decryption mode with the provided key and initialization vector
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        // Decrypt the encrypted message byte array using the cipher's doFinal method
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);

        // Convert the decrypted byte array back to a string and return it
        return new String(decryptedBytes);
    }
}

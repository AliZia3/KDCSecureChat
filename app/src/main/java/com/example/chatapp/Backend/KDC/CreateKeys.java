package com.example.chatapp.Backend.KDC;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CreateKeys {
    public SecretKey generateKey() {
        try {
            // Initialize the KeyGenerator
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // Specify the key size

            // Generate AES Key
            SecretKey secretKey = keyGen.generateKey();

            System.out.println("AES Secret Key: " + secretKey);

            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}

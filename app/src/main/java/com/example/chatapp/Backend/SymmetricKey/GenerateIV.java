package com.example.chatapp.Backend.SymmetricKey;

import java.security.SecureRandom;

import javax.crypto.spec.IvParameterSpec;

public class GenerateIV {
    public IvParameterSpec generateIV() {
        byte[] iv = new byte[16]; // Using 16 bytes IV for AES
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}

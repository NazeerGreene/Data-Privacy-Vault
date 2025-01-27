package com.example.demo.utils;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class MessageDigestImpl implements TokenGenerator {

    private final MessageDigest digest;

    public MessageDigestImpl() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }

    public MessageDigestImpl(@NonNull String algorithm) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(algorithm);
    }

    @Override
    public String tokenize(@NonNull String data) {
        // convert string to bytes
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        // run through hash algorithm
        byte[] hashedBytes = digest.digest(bytes);
        // convert bin to hex and return
        return toHexString(hashedBytes);
    }

    private String toHexString(byte[] bytes) {
        // convert the byte stream into signum representation;
        // this guarantees a positive representation of the hash
        BigInteger num = new BigInteger(1, bytes);

        // convert to base 16 representation
        StringBuilder hexString = new StringBuilder(num.toString(16));

        // padding, optional (1 octet = 2 hex char)
        while (hexString.length() < digest.getDigestLength() * 2) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}

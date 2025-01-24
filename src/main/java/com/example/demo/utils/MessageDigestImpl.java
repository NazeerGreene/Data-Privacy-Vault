package com.example.demo.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestImpl implements TokenGenerator {

    private final MessageDigest digest;

    public MessageDigestImpl() throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance("SHA-256");
    }

    public MessageDigestImpl(String algorithm) throws NoSuchAlgorithmException {
        digest = MessageDigest.getInstance(algorithm);
    }

    @Override
    public String tokenize(String data) {
        // convert string to bytes
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        // run through hash algorithm
        byte[] hashedBytes = digest.digest(bytes);
        // convert bin to hex and return
        return toHexString(hashedBytes, digest.getDigestLength());
    }

    private String toHexString(byte[] bytes, int digestLength) {
        // convert the byte stream into signum representation;
        // this guarantees a positive representation of the hash
        BigInteger num = new BigInteger(1, bytes);

        // convert to base 16 representation
        StringBuilder hexString = new StringBuilder(num.toString(16));

        // padding, optional
        while (hexString.length() < digestLength) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }
}

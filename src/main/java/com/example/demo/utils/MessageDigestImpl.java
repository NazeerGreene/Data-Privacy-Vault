package com.example.demo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenGeneratorSha256Impl implements TokenGenerator {

    private final MessageDigest alg = MessageDigest.getInstance("SHA-256");

    public TokenGeneratorSha256Impl() throws NoSuchAlgorithmException {
    }

    @Override
    public String tokenize(String data) {
        return "";
    }

    @Override
    public String detokenize(String token) {
        return "";
    }
}

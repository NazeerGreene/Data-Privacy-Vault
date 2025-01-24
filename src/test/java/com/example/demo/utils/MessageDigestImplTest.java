package com.example.demo.utils;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class MessageDigestImplTest {
    // Verified tokens using shasum 256

    @Test
    void shouldTokenizeWordNoSpaces() throws NoSuchAlgorithmException {
        String name = "Hello";
        String verifiedToken = "185f8db32271fe25f561a6fc938b2e264306ec304eda518007d1764826381969";

        MessageDigestImpl tokenizer = new MessageDigestImpl();
        String token = tokenizer.tokenize(name);

        assertEquals(verifiedToken, token);
    }

    @Test
    void shouldTokenizeWordWithSpaces() throws NoSuchAlgorithmException {
        String name = "John Doe";
        String verifiedToken = "6cea57c2fb6cbc2a40411135005760f241fffc3e5e67ab99882726431037f908";

        MessageDigestImpl tokenizer = new MessageDigestImpl();
        String token = tokenizer.tokenize(name);

        assertEquals(verifiedToken, token);
    }
}
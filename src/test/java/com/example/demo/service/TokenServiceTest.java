package com.example.demo.service;

import com.example.demo.model.RequestPayload;
import com.example.demo.repository.Database;
import com.example.demo.utils.MessageDigestImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {
    static String user = "user-0001";

    @BeforeAll
    static void setup() {
        Database.createUser(user);
    }

    @Test
    void shouldCreateNewToken() throws NoSuchAlgorithmException {
        TokenService service = new TokenService(new MessageDigestImpl());

        Map<String, String> fieldsAndValues = new HashMap<>();
        fieldsAndValues.put("field1", "value1");
        fieldsAndValues.put("field2", "value2");
        fieldsAndValues.put("field3", "value3");

        RequestPayload newRequest = new RequestPayload(user, fieldsAndValues);


    }
}
package com.example.demo.service;

import com.example.demo.model.RequestPayload;
import com.example.demo.model.ResponsePayload;
import com.example.demo.repository.Database;
import com.example.demo.utils.MessageDigestImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TokenServiceTest {
    static String user = "user-0001";
    static Map<String, String> fieldsAndValues = new HashMap<>();
    static Map<String, String> valueToHashMap = new HashMap<>();

    static TokenService service;

    @BeforeAll
    static void setup() {
        Database.createUser(user);

        try {
            service = new TokenService(new MessageDigestImpl());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        fieldsAndValues.put("field1", "value1");
        fieldsAndValues.put("field2", "value2");
        fieldsAndValues.put("field3", "value3");

        // hashes found using SHA256
        valueToHashMap.put("value1", "3c9683017f9e4bf33d0fbedd26bf143fd72de9b9dd145441b75f0604047ea28e");
        valueToHashMap.put("value2", "0537d481f73a757334328052da3af9626ced97028e20b849f6115c22cd765197");
        valueToHashMap.put("value3", "89dc6ae7f06a9f46b565af03eab0ece0bf6024d3659b7e3a1d03573cfeb0b59d");
    }

    @Test
    void shouldCreateNewTokens() {
        // set up the request
        RequestPayload newRequest = new RequestPayload(user, fieldsAndValues);
        // create new tokens
        RequestPayload response = service.createNewTokens(newRequest);
        // verify
        assertEquals(user, response.userId());
        assertEquals(valueToHashMap.get("value1"), response.data().get("field1"));
        assertEquals(valueToHashMap.get("value2"), response.data().get("field2"));
        assertEquals(valueToHashMap.get("value3"), response.data().get("field3"));
    }

    @Test
    void shouldDetokenizeTokens() {
        // set up the request
        RequestPayload newRequest = new RequestPayload(user, fieldsAndValues);
        // create new tokens
        RequestPayload response = service.createNewTokens(newRequest);
        // verify detokenized data is consistent
        ResponsePayload detokenized = service.detokenize(response);

        // no change to user id
        assertEquals(user, detokenized.id());

        // all values found
        assertEquals("value1", detokenized.data().get("field1").value());
        assertTrue(detokenized.data().get("field1").found());
        assertEquals("value2", detokenized.data().get("field2").value());
        assertTrue(detokenized.data().get("field2").found());
        assertEquals("value3", detokenized.data().get("field3").value());
        assertTrue(detokenized.data().get("field3").found());
    }

    @Test
    void shouldNotDetokenizeNonExistentData() {
        // set up the request
        RequestPayload newRequest = new RequestPayload(user, fieldsAndValues);
        // create new tokens
        RequestPayload response = service.createNewTokens(newRequest);
        response.data().put("fieldn", "invalid token");

        // verify non-existent detokenized data is not found
        ResponsePayload detokenized = service.detokenize(response);
        assertFalse(detokenized.data().get("fieldn").found(), "invalid token should not be found");
        assertEquals("", detokenized.data().get("fieldn").value());
    }
}
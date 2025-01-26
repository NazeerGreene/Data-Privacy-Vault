package com.example.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldCreateUser() {
        String user = "abc-0";

        Database.createUser(user);

        assertTrue(Database.userExists(user));
        assertEquals(0, Database.getAllDataFor(user).size());
    }

    @Test
    void shouldNotFindNonExistentUser() {
        assertFalse(Database.userExists("should-not-find"));
    }

    @Test
    void shouldAddAndFindDataForUser() {
        String user = "abc-1";
        String token = "token-123";
        String randomData = "random value";

        TokenizedData data = new TokenizedData(token, randomData);

        Database.createUser(user);
        Database.addDataFor(user, data);

        assertTrue(Database.userExists(user));
        List<TokenizedData> receivedData = Database.getAllDataFor(user);

        assertEquals(1, receivedData.size());
        assertEquals(token, receivedData.getFirst().token());
        assertEquals(randomData, receivedData.getFirst().value());
    }

    @Test
    void shouldNotFindDataForNonExistentUser() {
        String invalidUser = "abc-2!!!";
        String token = "token-123";
        String randomData = "random value";

        TokenizedData data = new TokenizedData(token, randomData);

        assertFalse(Database.userExists(invalidUser));

        List<TokenizedData> receivedData = Database.getAllDataFor(invalidUser);
        assertEquals(0, receivedData.size());
    }

    @Test
    void shouldReturnAllDataForUser() {
        String user = "abc-3";
        TokenizedData
                data1 = new TokenizedData("token-0101", "random random"),
                data2 = new TokenizedData("token-0111", "even more random");

        Database.createUser(user);
        Database.addDataFor(user, data1);
        Database.addDataFor(user, data2);

        List<TokenizedData> allData = Database.getAllDataFor(user);

        assertEquals(2, allData.size());
    }

    @Test
    void shouldReturnOnlyRequestedDataForUser() {
        String user = "abc-4";
        TokenizedData
                data1 = new TokenizedData("token-01", "random random"),
                data2 = new TokenizedData("token-02", "even more random"),
                data3 = new TokenizedData("token-03", "some more random");

        Set<String> tokens = new HashSet<>();
        tokens.add("token-01");
        tokens.add("token-02");

        Database.createUser(user);
        Database.addDataFor(user, data1);
        Database.addDataFor(user, data2);
        Database.addDataFor(user, data3);

        List<TokenizedData> allData = Database.getDataFor(user, tokens);

        assertEquals(2, allData.size());
        assertEquals("token-01", allData.getFirst().token());
        assertEquals("token-02", allData.getLast().token());
    }

    @Test
    void shouldRemoveDataForUser() {
        String user = "abc-6";
        String tokenToDelete = "token-01";
        TokenizedData
                data1 = new TokenizedData(tokenToDelete, "random random"),
                data2 = new TokenizedData("token-02", "even more random"),
                data3 = new TokenizedData("token-03", "some more random");

        Database.createUser(user);
        Database.addDataFor(user, data1);
        Database.addDataFor(user, data2);
        Database.addDataFor(user, data3);

        Database.removeDataFor(user, new TokenizedData(tokenToDelete, "whatever"));

        List<TokenizedData> allData = Database.getAllDataFor(user);

        assertEquals(2, allData.size());
        assertEquals("token-02", allData.getFirst().token());
        assertEquals("token-03", allData.getLast().token());
    }
}
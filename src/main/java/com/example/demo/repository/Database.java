package com.example.demo.repository;

import java.util.*;

public class Database {
    // mock database: String user-id, List: (token, data)
    private static final Map<String, List<TokenizedData>> database;

    static {
        database = new HashMap<>();
    }

    public static Optional<String> createUser(String userId) {
        return Optional.empty();
    }

    public static boolean userExists(String userId) {
        return false;
    }

    public static void addDataFor(String id, TokenizedData data) {

    }

    public static List<TokenizedData> getAllDataFor(String id) {
        return null;
    }

    public static List<TokenizedData> getDataFor(String id, Set<String> tokens) {
        return null;
    }

    public static void updateDataFor(String id, TokenizedData data) {

    }
}

package com.example.demo.repository;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class Database {
    // mock database: String user-userId, List: (token, value)
    private static final Map<String, List<TokenizedData>> database;

    static {
        database = new HashMap<>();
    }

    public static Optional<String> createUser(@NonNull String userId) {
        if (userExists(userId)) {
            return Optional.empty();
        }

        database.put(userId, new ArrayList<>());

        return Optional.of(userId);
    }

    public static boolean userExists(@NonNull String userId) {
        for(String id: database.keySet()) {
            if (id.equals(userId)) {
                return true;
            }
        }

        return false;
    }

    public static void addDataFor(@NonNull String userId, TokenizedData data) {
        if (userExists(userId)) {
            database.get(userId).add(data);
        }
    }

    public static List<TokenizedData> getAllDataFor(@NonNull String userId) {
        return Optional.ofNullable(database.get(userId))
                .orElseGet(Collections::emptyList);
    }

    public static List<TokenizedData> getDataFor(@NonNull String userId, @NonNull Set<String> tokens) {
        return getAllDataFor(userId)
                .stream()
                .filter(td -> tokens.contains(td.token()))
                .collect(Collectors.toList());
    }

    public static boolean removeDataFor(@NonNull String userId, TokenizedData data) {
        List<TokenizedData> userData = getAllDataFor(userId);
        return userData.remove(data);
    }
}

package com.example.demo.model;

import java.util.Map;

public record RequestPayload(String userId, Map<String, String> data) {
}

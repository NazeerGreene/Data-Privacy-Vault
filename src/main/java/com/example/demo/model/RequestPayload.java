package com.example.demo.model;

import java.util.Map;

public record RequestPayload(String id, Map<String, String> data) {
}

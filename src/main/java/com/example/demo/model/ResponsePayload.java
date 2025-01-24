package com.example.demo.model;

import java.util.Map;

public record ResponsePayload(String id, Map<String, ResponseField> data) {
}

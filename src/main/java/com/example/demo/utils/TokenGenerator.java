package com.example.demo.utils;

public interface TokenGenerator {
    String tokenize(String data);
    String detokenize(String token);
}

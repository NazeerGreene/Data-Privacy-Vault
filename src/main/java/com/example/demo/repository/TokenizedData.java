package com.example.demo.repository;

import java.util.Objects;

public record TokenizedData(String token, String value) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TokenizedData that = (TokenizedData) o;
        return Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, value);
    }
}

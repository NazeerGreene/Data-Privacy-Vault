package com.example.demo.service;

import com.example.demo.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenService {
    private final TokenGenerator tokenGenerator;

    public TokenService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }
}

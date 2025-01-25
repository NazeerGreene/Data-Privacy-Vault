package com.example.demo.service;

import com.example.demo.model.RequestPayload;
import com.example.demo.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    private final TokenGenerator tokenGenerator;

    @Autowired
    public TokenService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    public void createNewToken(RequestPayload payload) {
        // for each token in payload
        // tokenize
        // add to database for given user
    }

    public void detokenize(RequestPayload payload) {
        // for the given user
        // if token present:
        //  return (found, value)
    }
}

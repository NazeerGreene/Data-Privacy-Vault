package com.example.demo.controller;

import com.example.demo.model.RequestPayload;
import com.example.demo.repository.Database;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class TokenController {

    private final UserService userService;
    private final TokenService tokenService;
    private final TokenGenerator tokenGenerator;

    @Autowired
    public TokenController(UserService userService, TokenService tokenService, TokenGenerator tokenGenerator) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.tokenGenerator = tokenGenerator;
    }

    @PostMapping("/tokenizer")
    public ResponseEntity<RequestPayload> tokenizePayload(@RequestBody RequestPayload payload) {
        System.out.println(payload);

        if (!Database.userExists(payload.id())) {
            Database.createUser(payload.id());
        }

        tokenService.createNewToken(payload);
        // 200 if success; fail otherwise

        for(String key: payload.data().keySet()) {
            payload.data().compute(key, (k, v) -> this.tokenGenerator.tokenize(v));
        }

        return ResponseEntity.ok(payload);
    }

    @PostMapping("/detokenizer")
    public ResponseEntity<String> detokenizePayload(@RequestBody RequestPayload payload) {
        System.out.println(payload);

        if (!Database.userExists(payload.id())) {
            // fail
        }

        tokenService.detokenize(payload);
        // ResponsePayload for all tokens
    }
}

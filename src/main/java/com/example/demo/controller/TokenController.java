package com.example.demo.controller;

import com.example.demo.model.RequestPayload;
import com.example.demo.model.ResponsePayload;
import com.example.demo.repository.Database;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;
import com.example.demo.utils.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/tokenize")
    public ResponseEntity<RequestPayload> tokenizePayload(@RequestBody RequestPayload request) {
        if (!Database.userExists(request.userId())) {
            Database.createUser(request.userId());
        }

        RequestPayload response = tokenService.createNewTokens(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/detokenize")
    public ResponseEntity<ResponsePayload> detokenizePayload(@RequestBody RequestPayload request) {
        System.out.println(request);

        if (!Database.userExists(request.userId())) {
            return ResponseEntity.badRequest().build();
        }

        ResponsePayload response = tokenService.detokenize(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

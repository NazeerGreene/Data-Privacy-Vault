package com.example.demo.service;

import com.example.demo.model.RequestPayload;
import com.example.demo.model.TokenFoundField;
import com.example.demo.model.ResponsePayload;
import com.example.demo.repository.Database;
import com.example.demo.repository.TokenizedData;
import com.example.demo.utils.MessageDigestImpl;
import com.example.demo.utils.TokenGenerator;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TokenService {
    private final TokenGenerator tokenGenerator;

    public TokenService() throws NoSuchAlgorithmException {
        this.tokenGenerator = new MessageDigestImpl();
    }

    @Autowired
    public TokenService(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    public RequestPayload createNewToken(@NonNull RequestPayload request) {
        String userId = request.userId();
        RequestPayload response = new RequestPayload(userId, new HashMap<>());

        for(String key: request.data().keySet()) {
            String rawValue = clean(request.data().get(key));

            if (!rawValue.isEmpty()) {
                String token = this.tokenGenerator.tokenize(rawValue);

                TokenizedData dataToStore = new TokenizedData(token, rawValue);
                Database.addDataFor(userId, dataToStore);

                response.data().put(key, token);
            } else {
                response.data().put(key, rawValue);
            }
        }
        return response;
    }

    public ResponsePayload detokenize(@NonNull RequestPayload request) {
        // tokens to search for in database
        Set<String> tokens = request.data()
                .values()
                .stream()
                .filter(Objects::nonNull)
                .filter(v -> !v.isEmpty())
                .collect(Collectors.toSet());

        // the actual tokens and respective values found
        List<TokenizedData> detokenizedUserData = Database.getDataFor(request.userId(), tokens);

        Set<String> tokensFound = detokenizedUserData
                .stream()
                .flatMap(td -> Stream.of(td.token()))
                .collect(Collectors.toSet());

        // convenience mapper
        Map<String, String> tokenToValue = buildTokenToValueMap(detokenizedUserData);

        // prepare response
        ResponsePayload response = new ResponsePayload(request.userId(), new HashMap<>());

        request.data().forEach((field, token) -> {
            if (tokensFound.contains(token)) {
                response.data().put(field, new TokenFoundField(true, tokenToValue.get(token)));
            } else {
                response.data().put(field, new TokenFoundField(false, ""));
            }
        });

        return response;
    }

    private String clean(String text) {
        return (text == null) ? "" : text;
    }

    private Map<String, String> buildTokenToValueMap(@NonNull List<TokenizedData> tokenizedDataList) {
        Map<String, String> map = new HashMap<>();

        for(TokenizedData td: tokenizedDataList) {
            map.put(td.token(), td.value());
        }

        return map;
    }
}

package com.thoughtworks.lpe.be_template.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class TokenDecoder {
    @Value("${spring.auth0.custom-token-key}")
    private String customTokenKey;

    public String getTokenPayload(String accessToken) {
        String[] chunks = accessToken.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        return new String(decoder.decode(chunks[1]));
    }

    public String getCustomPropertyFromToken(String tokenPayload, String customPropertyToGet) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> object = objectMapper.readValue(tokenPayload, Map.class);

        return object.get(customTokenKey+ "/" +customPropertyToGet);
    }
}

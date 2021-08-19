package com.thoughtworks.lpe.be_template.security;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class TokenDecoder {
    public String getTokenPayload(String accessToken) {
        String[] chunks = accessToken.split("\\.");

        Base64.Decoder decoder = Base64.getDecoder();

        return new String(decoder.decode(chunks[1]));
    }
}

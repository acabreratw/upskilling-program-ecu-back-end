package com.thoughtworks.lpe.be_template.controllers.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    private final String errorCode;
    private final String developerMessage;
    private final String userMessage;

    public ErrorResponse(@JsonProperty("errorCode") String errorCode,
                         @JsonProperty("developerMessage") String developerMessage,
                         @JsonProperty("userMessage") String userMessage) {
        this.errorCode = errorCode;
        this.developerMessage = developerMessage;
        this.userMessage = userMessage;
        int i = 0;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
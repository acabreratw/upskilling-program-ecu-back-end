package com.thoughtworks.lpe.be_template.exceptions.enums;

public enum Error {
    NAME_IS_REQUIRED("001", "Name param is required", "Name is required"),
    SQL_SYNTAX_ERROR_EXCEPTION("002", "Sql syntax error"),
    INVALID_COURSE_ID("003", "Course id not found into the db", "Course id is invalid"),
    ENTITY_NOT_FOUND("004", "The object to update don't exist"),
    USER_NOT_FOUND("010", "User id not found into the db.", "User could not be found."),
    INVALID_FIELD_TOKEN("011", "Not known field value in token", "Invalid search in token field");

    private final String code;
    private final String developerMessage;
    private final String userMessage;

    Error(String code, String developerMessage, String userMessage) {
        this.code = code;
        this.developerMessage = developerMessage;
        this.userMessage = userMessage;
    }

    Error(String code, String userMessage) {
        this.code = code;
        this.developerMessage = "";
        this.userMessage = userMessage;
    }

    public String getCode() {
        return code;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}

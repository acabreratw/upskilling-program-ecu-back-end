package com.thoughtworks.lpe.be_template.exceptions;

import com.thoughtworks.lpe.be_template.exceptions.enums.Error;

public class LogicBusinessException extends RuntimeException {

    private Error error;

    public LogicBusinessException(Error error) {
        super(error.getDeveloperMessage());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}

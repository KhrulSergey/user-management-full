package com.khsa.usermanagement.util;

import java.util.Collection;

/**
 * Класс для представления JSON ответа в RESTful API
 */
public class ResponseMessage {

    private boolean success;
    private Collection<String> errors;

    public ResponseMessage() {
        this.success = true;
    }

    public ResponseMessage(Collection<String> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public Collection<String> getErrors() {
        return errors;
    }

}

package com.sr.authentication.customExceptions.exceptions;

public class InvalidTokenException extends Exception {

    public InvalidTokenException(String msg) {
        super(msg);
    }
}

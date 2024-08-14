package com.sr.gateway.customExceptions.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserTokenNotValid extends ResponseStatusException {
    public UserTokenNotValid() {
        super(HttpStatus.FORBIDDEN, "Invalid Token", null);

    }

}

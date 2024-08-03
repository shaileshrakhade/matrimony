package com.lagn.authentication.customExceptions;

import com.lagn.authentication.customExceptions.dto.ApiExceptionDto;
import com.lagn.authentication.customExceptions.exceptions.InvalidTokenException;
import com.lagn.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Locale;

@RestController
@ControllerAdvice
public class RestExceptionGlobalHandler {
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ApiExceptionDto> sQLException(SQLException ex) {
        ApiExceptionDto apiExceptionDto;
        if (ex.getMessage().contains("Duplicate entry")) {
            apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, "Mobile Number is already exist! Please try a login.");
        } else {
            apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameAlreadyExistException.class)
    public ResponseEntity<ApiExceptionDto> UsernameAlreadyExistException() {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, "Mobile Number is already exist! Please try a login.");
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ApiExceptionDto> invalidTokenException() {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, "Security Token is Not Valid");
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> usernameNotFoundException() {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, "Bad Credential");
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }
}

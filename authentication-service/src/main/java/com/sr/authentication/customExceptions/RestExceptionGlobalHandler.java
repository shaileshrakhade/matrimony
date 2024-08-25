package com.sr.authentication.customExceptions;

import com.sr.authentication.customExceptions.dao.ApiExceptionDto;
import com.sr.authentication.customExceptions.exceptions.InvalidOtpException;
import com.sr.authentication.customExceptions.exceptions.InvalidTokenException;
import com.sr.authentication.customExceptions.exceptions.PhoneNumberAlreadyExistException;
import com.sr.authentication.customExceptions.exceptions.UsernameAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@ControllerAdvice
@Slf4j
public class RestExceptionGlobalHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiExceptionDto> exception(Exception ex) {
        log.error("Exception ${}", ex.getMessage());
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidOtpException.class)
    public ResponseEntity<ApiExceptionDto> invalidOtpException(InvalidOtpException ex) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<ApiExceptionDto> sQLException(SQLException ex) {
        log.error("SQLException ${}", ex.getMessage());
        ApiExceptionDto apiExceptionDto;
        if (ex.getMessage().contains("Duplicate entry")) {
            apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, "Username is already exist! Please try a login.");
        } else {
            apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UsernameAlreadyExistException.class)
    public ResponseEntity<ApiExceptionDto> UsernameAlreadyExistException(UsernameAlreadyExistException ex) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PhoneNumberAlreadyExistException.class)
    public ResponseEntity<ApiExceptionDto> PhoneNumberAlreadyExistException(PhoneNumberAlreadyExistException ex) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ApiExceptionDto> invalidTokenException(InvalidTokenException ex) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> usernameNotFoundException(UsernameNotFoundException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }
}

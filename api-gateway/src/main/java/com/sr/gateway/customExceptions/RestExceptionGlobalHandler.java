package com.sr.gateway.customExceptions;

import com.sr.gateway.customExceptions.dto.ApiExceptionDto;
import com.sr.gateway.customExceptions.exceptions.InvalidTokenException;
import com.sr.gateway.customExceptions.exceptions.UserTokenNotValid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class RestExceptionGlobalHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiExceptionDto> exception(Exception e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserTokenNotValid.class)
    public ResponseEntity<ApiExceptionDto> bioDataNotFoundException(UserTokenNotValid e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ApiExceptionDto> bioDataNotFoundException(InvalidTokenException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.UNAUTHORIZED, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.UNAUTHORIZED);
    }


}

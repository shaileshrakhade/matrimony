package com.matrimony.admin.customExceptions;

import com.matrimony.admin.customExceptions.dto.ApiExceptionDto;
import com.matrimony.admin.customExceptions.exceptions.InvalidTokenException;
import com.matrimony.admin.masters.exceptions.MasterAttributesAlreadyExitException;
import com.matrimony.admin.masters.exceptions.MasterAttributesNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class RestExceptionGlobalHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiExceptionDto> exception(Exception ex) {
        log.error("Exception ${}", ex.getMessage());
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity<ApiExceptionDto> invalidTokenException(InvalidTokenException ex) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MasterAttributesAlreadyExitException.class)
    public ResponseEntity<ApiExceptionDto> masterAttributesAlreadyExitException(MasterAttributesAlreadyExitException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.IM_USED, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.IM_USED);
    }

    @ExceptionHandler(value = MasterAttributesNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> bioDataNotFoundException(MasterAttributesNotFoundException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.NOT_FOUND);
    }

}

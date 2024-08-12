package com.matrimony.biodata.customExceptions;

import com.matrimony.biodata.customExceptions.dto.ApiExceptionDto;
import com.matrimony.biodata.customExceptions.exceptions.BioDataAlreadyApproveException;
import com.matrimony.biodata.customExceptions.exceptions.BioDataNotFoundException;
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

    @ExceptionHandler(value = BioDataNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> bioDataNotFoundException(BioDataNotFoundException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BioDataAlreadyApproveException.class)
    public ResponseEntity<ApiExceptionDto> bioDataAlreadyApproveException(BioDataAlreadyApproveException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.LOCKED, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.LOCKED);
    }
}

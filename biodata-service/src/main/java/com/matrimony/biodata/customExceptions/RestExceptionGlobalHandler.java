package com.matrimony.biodata.customExceptions;

import com.matrimony.biodata.customExceptions.dto.ApiExceptionDto;
import com.matrimony.biodata.customExceptions.exceptions.*;
import com.matrimony.biodata.masters.exceptions.MasterAttributesAlreadyExitException;
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
        log.error("InvalidTokenException ${}", ex.getMessage());
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage());
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

    @ExceptionHandler(value = BioDataAlreadyExistException.class)
    public ResponseEntity<ApiExceptionDto> bioDataAlreadyExistException(BioDataAlreadyExistException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.FOUND);
    }

    @ExceptionHandler(value = BioDataLockException.class)
    public ResponseEntity<ApiExceptionDto> bioDataLockException(BioDataLockException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.FORBIDDEN, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = BioDataRegistrationCloseException.class)
    public ResponseEntity<ApiExceptionDto> bioDataLockException(BioDataRegistrationCloseException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = MasterAttributesAlreadyExitException.class)
    public ResponseEntity<ApiExceptionDto> masterAttributesAlreadyExitException(MasterAttributesAlreadyExitException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.NOT_FOUND);
    }
}

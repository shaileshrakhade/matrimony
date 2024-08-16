package com.matrimony.favorite.customExceptions;

import com.matrimony.favorite.customExceptions.dto.ApiExceptionDto;
import com.matrimony.favorite.customExceptions.exceptions.AlreadyAddedInFavorateException;
import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import com.matrimony.favorite.customExceptions.exceptions.FavoriteNotFoundException;
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


    @ExceptionHandler(value = AlreadyAddedInFavorateException.class)
    public ResponseEntity<ApiExceptionDto> bioDataAlreadyExistException(AlreadyAddedInFavorateException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.FOUND);
    }
    @ExceptionHandler(value = FavoriteNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> favoriteNotFoundException(FavoriteNotFoundException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.FOUND);
    }

    @ExceptionHandler(value = BioDataNotFoundException.class)
    public ResponseEntity<ApiExceptionDto> bioDataNotFoundException(BioDataNotFoundException e) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(HttpStatus.FOUND, e.getMessage());
        return new ResponseEntity<ApiExceptionDto>(apiExceptionDto, HttpStatus.FOUND);
    }
}

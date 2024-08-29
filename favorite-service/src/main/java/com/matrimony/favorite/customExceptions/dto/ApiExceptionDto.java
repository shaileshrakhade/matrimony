package com.matrimony.favorite.customExceptions.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ApiExceptionDto {
    private HttpStatus httpStatus;
    private String message;
    private Date date=new Date();

    public ApiExceptionDto(HttpStatus httpStatus, String message) {
        this.httpStatus=httpStatus;
        this.message=message;
    }
}

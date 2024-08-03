package com.lagn.authentication.customExceptions.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ApiExceptionDto {
    private HttpStatus httpStatus;
    private String errorMessage;
    private Date date=new Date();

    public ApiExceptionDto(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus=httpStatus;
        this.errorMessage=errorMessage;
    }
}

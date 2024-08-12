package com.sr.authentication.customExceptions.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenDto {
    private String message;
    private String token;
    private Date date=new Date();
}
package com.matrimony.favorite.service;

import com.matrimony.favorite.customExceptions.exceptions.BioDataNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface BiodataService {
    public  boolean isPresent(HttpServletRequest request, String biodataId) throws  ExecutionException, InterruptedException;

}
